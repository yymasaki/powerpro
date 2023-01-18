$(function() {
	
	//ローディング画面の設定
	var stopload= function(){
		$('.loading').hide();	
	}
	stopload();
	
	//初期表示では非活性
	$(".delete-button").prop("disabled", true);
	$('#adminComment').on("keydown keyup keypress change", function() {
		//スペースと改行を無視した文字数
		let len = $('#adminComment').val().replace(/[\n\s　]/g, "").length;
		if (len < 1) {
			$(".delete-button").prop("disabled", true);
		} else {
			$(".delete-button").prop("disabled", false);
		}
	});
	
	//削除ボタンを押下後、非活性化にする.処理中を表示
	$('.delete-button').on('click', function(){
		$('.loading').show();
		setTimeout(stopload, 20000);
		$(this).prop('disabled', true);
		$('#delete-modal').submit();
	});

	// 戻るボタンを押した時の処理
	$('#exampleModalCenter').on('hide.bs.modal', function(event) {
		let confirmIndex = parseInt($('.confirm').length) - 1;
		// クローンが存在する場合に削除
		if (confirmIndex > 0) {
			for (i = confirmIndex; i > 0; i--) {
				$('.confirm:last').remove();
			}
		}
	})
	
	//アラートバーをフェードアウトする
	const fadeOutAlert = function(){
		$("#flash").fadeOut("slow");
	}
	setTimeout(fadeOutAlert, 3000);
});