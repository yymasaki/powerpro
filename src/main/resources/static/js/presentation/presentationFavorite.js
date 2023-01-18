'use strict'
window.onpageshow = () => {
    let favoriteButton = document.getElementById('favoriteButton');
    let favoriteCount = document.getElementById('favoriteCount');
    let presentationId = document.getElementById('presentationId');
    let userId = document.getElementById('userId');
    console.log("presentationId:" + presentationId.value);
    console.log("userId:" + userId.value)

    // ページを読み込んだ際に総いいね数とユーザーがいいねしているかを取得
    const url = new URL('http://localhost:8080/presentation/searchFavorite');
    url.searchParams.append('presentationId', presentationId.value);
    fetch(url)
        .then(responce => {
            return responce.json();
        })
        .then(favoriteList => {
            if(favoriteList.length == 0){
                favoriteButton.textContent = 'いいね';
            }else{
                for (const favorite of favoriteList) {                  
                    if (favorite.userId == userId.value) {
                        favoriteButton.textContent = 'いいね解除';
                    } else {
                        favoriteButton.textContent = 'いいね';
                    }
                }
            }
            favoriteCount.textContent = favoriteList.length;
        })
        .catch(error => {
            console.error("通信失敗 : ", error);
        })

    // いいねボタンが押された時の挙動
    favoriteButton.onclick = (e) => {
        if (favoriteButton.textContent === 'いいね') {
            console.log("いいねが押された");
            const url2 = new URL('http://localhost:8080/presentation/favoriteDo');
            url2.searchParams.append('presentationId', presentationId.value);
            fetch(url2)
                .then(responce => {
                    return responce.json();
                })
                .then(favoriteDoList => {
                    favoriteButton.textContent = 'いいね解除';
                    favoriteCount.textContent = favoriteDoList.length;
                })
                .catch(error => {
                    console.error("通信失敗 : ", error);
                })

        } else if (favoriteButton.textContent === 'いいね解除') {

            console.log("いいね解除が押された");
            const url3 = new URL('http://localhost:8080/presentation/favoriteUnDo');
            url3.searchParams.append('presentationId', presentationId.value);
            fetch(url3)
                .then(responce => {
                    return responce.json();
                })
                .then(favoriteUnDoList => {
                    favoriteButton.textContent = 'いいね';
                    favoriteCount.textContent = favoriteUnDoList.length;
                })
                .catch(error => {
                    console.error("通信失敗 : ", error);
                })
        }

    }

}
