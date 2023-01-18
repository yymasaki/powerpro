$(function(){
	
	// 入力チェック用
	$("#done").prop("disabled",true);
	var visible_current_password = false;
	var visible_new_password = false;
	var visible_check_password = false;
	
	// メッセージを非表示
	var hideAlert = function() {
		$("#flash").hide();
	}
	setTimeout(hideAlert, 3000);
	
	$("input[name='currentPassword']").click(function(){
		$("#current-password-error1").text("");
	});
	
	$("input[name='currentPassword']").blur(function(){
		var hostUrl =  location.href +"/pass-check-api"
		var currentPassword = $(this).val();

		$.ajax({
			url: hostUrl,
			type: "GET",
			dataType: "json",
			data:{
				password: currentPassword ,
			},
			async: true
		}).done(function(data){
			
			if(data.passwordError != null){
				visible_current_password = false;
				$("#current-password-error2").text(data.passwordError);
			}else{
				visible_current_password = true;
				$("#current-password-error2").text("");
			}
			
			if($("input[name='newPassword']").val() == $("input[name='currentPassword']").val() ){
				visible_new_password = false;
				$("#new-password-error2").text("新パスワードが現在のパスワードと同じです");
			}else{
				visible_new_password = true;
				$("#new-password-error2").text("");
			}
			
			if($("input[name='currentPassword']").val()=='' || $("input[name='currentPassword']").val()==null){
				visible_current_password = false;
				visible_new_password = false;
				$("#current-password-error2").text("");
				$("#new-password-error2").text("");
			}
			
			button_status();
		})
	});
	
	$("input[name='newPassword']").on("keyup", function(){
		if(!$(this).val().match(/^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])[!-~]{8,20}$/) ){
			visible_new_password = false;
			$("#new-password-error1").text("英大文字・英小文字・数字を全て含む8-20字(半角)で入力してください");
		}else{
			visible_new_password = true;
			$("#new-password-error1").text("");
		}
		if($(this).val() != $("input[name='checkPassword']").val() ){
			visible_check_password = false;
			$("#check-password-error").text("確認用パスワードが一致しません");
		}else{
			visible_check_password = true;
			$("#check-password-error").text("");
		}
		if($("input[name='checkPassword']").val()==''){
			visible_check_password = false;
			$("#check-password-error").text("");
		}
		button_status();
	});
	
	$("input[name='checkPassword']").on("keyup", function(){
		var password = $("input[name='newPassword']").val();
		if($(this).val() != password ){
			visible_check_password = false;
			$("#check-password-error").text("確認用パスワードが一致しません");
		}else{
			visible_check_password = true;
			$("#check-password-error").text("");
		}
		if($("input[name='checkPassword']").val()==''){
			visible_check_password = false;
			$("#check-password-error").text("");
		}
		button_status();
	});
	
	$("input[name='newPassword']").on("keyup", function(){
		if($(this).val() == $("input[name='currentPassword']").val() ){
			visible_new_password = false;
			$("#new-password-error2").text("新パスワードが現在のパスワードと同じです");
		}else{
			visible_new_password = true;
			$("#new-password-error2").text("");
		}
		if($("input[name='currentPassword']").val()==''){
			visible_new_password = false;
			$("#current-password-error2").text("");
			$("#new-password-error2").text("");
		}
	});
	
	function button_status() {
		if(visible_current_password && visible_new_password && visible_check_password){
			$("#done").prop("disabled",false);
		}else{
			$("#done").prop("disabled",true);
		}
	}
	
	// パスワードの表示・非表示切替
	$("#toggle-password").on('click', function() {
	  // iconの切り替え
	  $(this).toggleClass("fa-eye fa-eye-slash");
	  
	  // type切替
	  if ($("#current-password").attr("type") == "password") {
		 //password→text
		$("#current-password").attr("type", "text");
		$("#new-password").attr("type", "text");
		$("#check-password").attr("type", "text");
	  } else {
		  //text→password
		  $("#current-password").attr("type", "password");
		  $("#new-password").attr("type", "password");
		  $("#check-password").attr("type", "password");
	  }
	});
});