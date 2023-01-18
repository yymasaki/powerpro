$(function() {
	
	//ローディング画面の設定
	var stopload= function(){
		$('.loading').hide();	
	}
	
	stopload();
	
 	//管理者コメントが入力されていない場合否認ボタン非活性化
	$('#adminComment').on("keydown keyup keypress change", function() {
		if($('#process').text() === "否承認"){
			let len = $('#adminComment').val().replace(/[\n\s　]/g, "").length;
			if (len < 1) {
				$("#done").prop("disabled", true);
			} else {
				$("#done").prop("disabled", false);
			}			
		}
	});

	// 送信ボタンをクリック時の処理
	$('#exampleModal').on('show.bs.modal', function(event) {
		const button = $(event.relatedTarget); 
		const recipient = button.data('whatever'); 
		const modal = $(this);
		let pcName = $('#pc').text();
		let mobileName = $('#mobile').text();
		let adminComment = $('#adminComment').val();
		
		// 確定ボタンに処理名を表示
		if (recipient === "approval") {
			$('#stage').attr('value', "0");
			modal.find('#process').text("承認");
			modal.find('#requirement').text("（任意）").css('color', 'black');
			$('#sentense').text("申請を承認して良いですか？");
			$("#done").prop("disabled", false);
		} else if (recipient === "disapproval") {
			$('#stage').attr('value', "3");
			modal.find('#process').text("否承認");
			modal.find('#requirement').text("（必須）").css('color', 'red');
			$('#sentense').text("申請を否承認して良いですか？");
			if(adminComment.length === 0){
				$("#done").prop("disabled", true);				
			}else{
				$("#done").prop("disabled", false);		
			}
		} else if (recipient === "delete") {
			$('#stage').attr('value', "9");
			modal.find('#process').text("取消");
			$('#sentense').text("申請を取り消して良いですか？");
			$("#done").prop("disabled", false);
		}
		
		//一度承認ボタンを押下後、非活性化にする.処理中の表示.
		$('.modal-button').on('click', function(){
			$('.loading').show();
			setTimeout(stopload, 20000);
			$(this).prop('disabled', true);
			$('.process').submit();
		});
	})

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
	
	//アラートバーを消す
	const fadeOutAlert = function(){
		$("#flash").fadeOut("slow");
	}
	setTimeout(fadeOutAlert, 3000);

});