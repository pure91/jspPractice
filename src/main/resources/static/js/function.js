/* 전역변수 */
let urlPre = "http://localhost:8080/";

/* 엑셀 데이터 조회 */
function excelListTable() {
    let url = urlPre + 'api/excel/search-excel';

    axios.get(url).then(function (res) {
        let excelList = res.data;

        let elements = document.querySelectorAll('.excelList');
        elements.forEach(function (element) {
            element.remove();
        })

        let html = `
        <div class='excelList'>
            <div id='downloadBtn'>
                <form name='excelFormData' id='targetForm' method='GET' action='http://localhost:8080/api/excel/downloadExcel' onsubmit='excelsubmit();'>
                    <input type='hidden' id='excelChk' name='excelChk' value='' />
                    <input type='hidden' id='excelA' name='excelA' value='' />
                    <input type='hidden' id='excelB' name='excelB' value='' />
                    <input type='hidden' id='excelC' name='excelC' value='' />
                    <input type='submit' id='targetBtn' value='Excel Download' />
                </form>
            </div>
            <table class='excelTable' style='width:100%;'>
                <tr>
                    <th><input type='checkbox' name='masterChkbox' onclick='excelChkFunction(this)' /></th>
                    <th>idx</th>
                    <th>excelA</th>
                    <th>excelB</th>
                    <th>excelC</th>
                </tr>`;

        excelList.forEach((element) => {
        let excelParams = element.excelA + "-" + element.excelB + "-" + element.excelC;
        html += `
            <tr>
                <th><input type='checkbox' name='chkbox' id='${element.idx}' value='${excelParams}'></th>
                <th>${element.idx}</th>
                <th>${element.excelA}</th>
                <th>${element.excelB}</th>
                <th>${element.excelC}</th>
            </tr>`;
        });
        html += `
            </table>
        </div>`;

        let excelListDiv = document.querySelector('.excelListDiv');
        excelListDiv.innerHTML = html;
    });
}

// excel 다운로드
function excelsubmit() {
    console.log("Excel Print");

    let chkBox = $("input:checkbox[name=chkbox]:checked");
    let excelA = [];
    let excelB = [];
    let excelC = [];

    document.getElementById("excelChk").value = 0;
    document.getElementById("excelA").value = "";
    document.getElementById("excelB").value = "";
    document.getElementById("excelC").value = "";

    if (chkBox.length != 0) {
        alert("excel list" + chkBox.length + "개 download");
        for (let chkcnt = 0; chkcnt < chkBox.length; chkcnt++) {
            let excelSplitTmp = chkBox[chkcnt].value.split("-");

            excelA.push(excelSplitTmp[0]);
            excelB.push(excelSplitTmp[1]);
            excelC.push(excelSplitTmp[2]);
        }

        document.getElementById("excelChk").value = chkBox.length;
        document.getElementById("excelA").value = excelA.join(",");
    } else {
        alert("not chk");
    }
}

// 체크박스 선택/해제
function excelChkFunction(ele) {
    const chkbox = document.getElementById("chkbox");
    chkbox.forEach((box) => {
        box.checked = ele.checked;
    })
}