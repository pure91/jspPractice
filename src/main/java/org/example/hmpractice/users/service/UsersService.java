package org.example.hmpractice.users.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.math3.analysis.function.Max;
import org.example.hmpractice.files.dto.UsersProfileDTO;
import org.example.hmpractice.users.dto.UsersDTO;
import org.example.hmpractice.users.mapper.UsersMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Log4j2
@Service
public class UsersService {

    @Autowired
    UsersMapper usersMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Value("${file.upload-dir}")
    private String uploadDir;

    // 파일 크기 제한
    @Value("${file.upload.max-size}")
    private int maxFileSize;

    // 이메일 중복 확인
    public boolean isEmailExists(String email) {
        int count = usersMapper.countByEmail(email);
        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    // 회원가입
    public void registerUser(UsersDTO usersDTO) {
        String encodedPassword = passwordEncoder.encode(usersDTO.getPassword());
        usersDTO.setPassword(encodedPassword);
        int result = usersMapper.registerUserInfo(usersDTO);

        if (result == 0) {
            throw new RuntimeException("회원가입에 실패하였습니다");
        }

        log.info("GeneratedKeys로 미리 생성된 사용자 ID: {}", usersDTO.getId());
    }

    // 프로필 사진 업로드
    public void saveUserProfile(int id, MultipartFile profileImage) throws Exception {
        try {
            // 프로필 이미지 저장
            String filePath = saveProfileImage(profileImage);

            // db 저장
            UsersProfileDTO usersProfileDTO = new UsersProfileDTO();
            usersProfileDTO.setUserId(id);
            usersProfileDTO.setFilePath(filePath);
            usersProfileDTO.setFileName(profileImage.getOriginalFilename());
            usersProfileDTO.setFileSize(profileImage.getSize());
            usersProfileDTO.setFileType(profileImage.getContentType());

            log.info("usersProfileDTO:{}", usersProfileDTO);

            int count = usersMapper.insertFile(usersProfileDTO);

        } catch (IOException e) {
            throw new Exception("파일 저장 중 오류 발생", e);
        } catch (Exception e) {
            throw new Exception("프로필 저장 중 오류 발생", e);
        }
    }

    // 프로필 이미지 저장
    private String saveProfileImage(MultipartFile profileImage) throws IOException {
        if (profileImage.getSize() > maxFileSize) {
            throw new IOException("5MB 이하의 파일을 업로드해 주세요.");
        }

        // 파일 확장자 체크(이미지만 허용)
        String fileName = profileImage.getOriginalFilename();
        String fileExtension = fileName.substring(fileName.lastIndexOf(".") +1).toLowerCase();
        //TODO 나중에 이런 파일확장자는 따로 DB에 테이블 만들어서 거기서 꺼내오기 하드코딩 ㄴㄴ
        if (!Arrays.asList("jpg", "jpeg", "png", "gif").contains(fileExtension)) {
            throw new IOException("지원되지 않는 파일 형식입니다. 이미지 파일만 업로드 가능합니다.");
        }

        // 저장 경로 설정(중복 이름 방지, toString은 혹시 특수문자 문제 생길까봐)
        String uniqueFileName = UUID.randomUUID().toString() + "_" + fileName;

        File dir = new File(uploadDir);
        if (!dir.exists()) {
            dir.mkdirs();  // 디렉토리가 없으면 생성
        }

        // 지정한 경로에 저장
        File file = new File(uploadDir, uniqueFileName);
        profileImage.transferTo(file); // 파일 저장

        return uploadDir + uniqueFileName; /// 파일명 반환(DB에 저장할 파일)
    }

    // 사용자 목록 조회(페이징)
    public PageInfo<UsersDTO> getAllUsers(int pageNum, int pageSize, String keyword) {
        PageHelper.startPage(pageNum, pageSize);
        List<UsersDTO> userList = usersMapper.getUserList(keyword);
        return new PageInfo<>(userList);
    }

    // 사용자 정보 조회
    public UsersDTO getUserInfo(int id) {
        return usersMapper.getUserDetail(id);
    }

    // 사용자 정보 수정
    public void updateUserInfo(UsersDTO usersDTO) {
        int result = usersMapper.updateUserInfo(usersDTO);

        if (result == 0) {
            throw new RuntimeException("정보수정에 실패하였습니다");
        }
    }

    // 사용자 삭제
    public void deleteUser(int id) {
        int result = usersMapper.deleteUser(id);

        if (result == 0) {
            throw new RuntimeException("삭제에 실패하였습니다");
        }
    }

    // User 로그인 정보 조회
    public UsersDTO findByEmail(String email) {
        return usersMapper.getUserEmail(email);
    }

}
