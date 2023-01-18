$(function(){
	
	$('#done').prop('disabled',true);

	//ローディング画面の設定
	var stopload= function(){
		$('.loading').hide();	
	}
	stopload();
	
	// 送信ボタン１回押下したらボタン無効化する.処理中を表示
	$('#done').on('click',function(){
		$('.loading').show();
		setTimeout(stopload, 20000);
		$('#emailForm').submit();
		$(this).prop('disabled',true);
	});
	
	$("input[name='email']").on("keyup", function(){
		var email = $(this).val().replace(/\s+/g, "");
		if(!email.match(/^[a-zA-Z.0-9]{1,30}$/) ){
			$('#done').prop('disabled',true);
			$("#email-error").text("入力値が不正です ※記号は半角ドットのみ入力可");
		}else{
			$('#done').prop('disabled',false);
			$(this).val($(this).val().replace(/\s+/g, ""));
			$("#email-error").text("");
		}
	});

			
});