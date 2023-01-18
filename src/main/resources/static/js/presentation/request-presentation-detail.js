'use strict'
window.onpageshow = () => {

    let loading = document.getElementById('loading');
    let appendButton = document.getElementById('approveButton');
    let fixButton = document.getElementById('fixButton');
    let rejectButton = document.getElementById('rejectButton');
    let tmpApprovedButton = document.getElementById('tmpApprovedButton');
    let cancelApprove = document.getElementById('cancelApprove');
    let inputPresentationDate = document.querySelector(`input[type='date'][name='presentationDate']`);
    let inputAdminComment = document.querySelector(`textarea[name='adminComment']`);

    //ローディング画面の設定
    let showloading = loading.style.display;
    loading.style.display = 'none';
    let hideloading = loading.style.display;
    console.log(showloading.style);
    console.log(hideloading.style);


    // 承認ボタンを押下した際の処理
    appendButton.onclick = (e) => {
        let now = new Date();
        let year = now.getFullYear();
        let month = now.getMonth() + 1;
        let date = ('0' + now.getDate()).slice(-2);
        now = `${year}-${month}-${date}`;

        // 日付が選択されていない場合
        if (!inputPresentationDate.value) {
            alert('日付が選択されていません');
            return false;
        } else if (now > inputPresentationDate.value) { // 過去日を選択している場合
            alert('過去日は選択できません');
            return false;
        } else {
            loading.style.display = showloading;
            setTimeout(hideloading, 20000);
        }
    }

    fixButton.onclick = (e) => {
        // コメントが入力されていない場合
        if (!inputAdminComment.value) {
            alert('コメントが空欄です。コメントを入力してください。');
            return false;
        } else {
            loading.style.display = showloading;
            setTimeout(hideloading, 20000);
        }
    }

    // ローディング画面の表示
    tmpApprovedButton.onclick = (e) => {
        loading.style.display = showloading;
        setTimeout(hideloading, 20000);
    }
    rejectButton.onclick = (e) => {
        loading.style.display = showloading;
        setTimeout(hideloading, 20000);

    }
    cancelApprove.onclick = (e) => {
        loading.style.display = showloading;
        setTimeout(hideloading, 20000);

    }

}