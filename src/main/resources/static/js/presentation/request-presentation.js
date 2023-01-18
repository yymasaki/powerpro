'use strict'

$(function(){
	let presentationId;
	let preferredDate;
	let dateLink;
	
	// 「日付を確定」リンクを押した際の処理
	$(document).on("click", "#date-link", function(){
		presentationId = $(this).parent().children('input[name=presentationId]').val();
		preferredDate = $(this).parent().children('span[name=preferredDate]');
		dateLink = $(this).parent().children('a[name=date-link]');
		console.log('presentationId:' + presentationId);
		console.log('preferredDate:' + preferredDate);
	});
	
	// 確定ボタンの処理
	$('.btn-primary').on('click', function(){
		let presentationDate = $('#presentationDate').val();
		let now = new Date();// 現在の日時を取得
		let year = now.getFullYear();
		let month = now.getMonth() + 1; // 月は 0～11 の範囲で指定するため+1する
		let date = ('0' + now.getDate()).slice(-2); // -2は最後から2番目の文字
		now = year + '-' + month + '-' + date; // 再代入
		console.log(now);
		
		// 日付が選択されていない場合
		if (!presentationDate.length) {
			alert("日付が選択されていません。");
			return false;
		}
		
		// 過去日は選択できない
		if (now > presentationDate) {
			alert("過去日は選択できません。");
			return false;
		}
		
		// 以下、発表会日時の確定可否
		let check = window.confirm("発表会の日時を確定します。\r\n以後、この発表会データは「承認」となり、本画面では編集不可となります。\r\nよろしいですか？");
		if (check) {
			let stage = 0;// stage=0(承認)
			console.log('presentationId:' + presentationId);
			console.log('stage:' + stage);
			console.log(presentationDate);
		
			const url = new URL(window.location + '/update-presentation');
			const params = new URLSearchParams([['presentationId', presentationId], ['stage', stage], ['presentationDate', presentationDate]]);
			url.search = params;
			console.log(url.href);
			fetch(url)
				.then(response => {
					return response.json;
				})
				.then(data => {
					console.log(data)
				})
				.catch(error => {
					console.error("通信失敗 : ", error);
			})
			
			alert("発表日時を確定し「承認」となりました。\r\n再度編集する場合は、申請詳細画面からステータスを「仮承認」に修正してください");
			
			// 確定日に書き換える
			preferredDate.text(presentationDate);
			// 日付選択リンクは非表示にする
			dateLink.hide();
			return true; // 確認ダイアログで「ok」の場合、処理を続行
		} else {
			return false; // 「キャンセル」の場合、処理を中断
		}
	})
	// 戻るボタンの処理
	$('.btn-secondary').on('click', function(){
		$('.modal').modal('hide');
	})
})