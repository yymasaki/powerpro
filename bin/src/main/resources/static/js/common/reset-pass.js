$(function(){
	
	// 入力チェック用
	$("#submit").prop("disabled",true);
	var visible_password = false;
	var visible_check_password = false;
	
	$("input[name='password']").on("keyup", function(){
		if(!$(this).val().match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[!-~]{8,20}$/) ){
			visible_password = false;
			$("#password-error").text("英大文字・英小文字・数字を全て含む8-20字(半角)で入力してください");
		}else{
			visible_password = true;
			$("#password-error").text("");
		}
		if($(this).val() != $("input[name='checkPassword']").val() ){
			visible_check_password = false;
			$("#check-password-error").text("確認用パスワードが一致しません");
		}else{
			visible_check_password = true;
			$("#check-password-error").text("");
		}
		button_status();
	});
	
	$("input[name='checkPassword']").on("keyup", function(){
		var password = $("input[name='password']").val();
		if($(this).val() != password ){
			visible_check_password = false;
			$("#check-password-error").text("確認用パスワードが一致しません");
		}else{
			visible_check_password = true;
			$("#check-password-error").text("");
		}
		button_status();
	});
	
	function button_status() {
		if(visible_password && visible_check_password){
			$("#submit").prop("disabled",false);
		}else{
			$("#submit").prop("disabled",true);
		}
	}
	
	// パスワードの表示・非表示切替
	$("#toggle-password").on('click', function() {
	  // iconの切り替え
	  $(this).toggleClass("fa-eye fa-eye-slash");
	  
	  // type切替
	  if ($("input[name='password']").attr("type") == "password") {
		 //password→text
		  $("input[name='password']").attr("type", "text");
		  $("input[name='checkPassword']").attr("type", "text");
	  } else {
		//text→password
		  $("input[name='password']").attr("type", "password");
		  $("input[name='checkPassword']").attr("type", "password");
	  }
	});
});