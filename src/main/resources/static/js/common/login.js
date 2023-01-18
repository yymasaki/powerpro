$(function(){
	
	//ローディング画面の設定
	var stopload= function(){
		$('.loading').hide();	
	}
	
	stopload();
	
	// ログインボタン押下したら処理中を表示.
	$('#done').on('click',function(){
		$('.loading').show();
		setTimeout(stopload, 20000);
	});
	
	// メッセージを非表示
	var hideAlert = function() {
		$("#flash").hide();
	}
	setTimeout(hideAlert, 3000);

});