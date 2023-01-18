$(function(){
	
	// 送信ボタン１回押下したらボタン無効化する
	$('#done').on('click',function(){
		$(this).prop('disabled',true);
		$('#registerUserForm').submit();
	});

	// 入力チェック用
	$("#modal-open").prop("disabled",true);
	$("#check-datas-error").text("未入力または不正な入力項目があります");
	var visible_last_name = false;
	var visible_first_name = false;
	var visible_hired = false;
	var visible_department = false;
	var visible_email = false;
	var not_deplicated_email = false;
	var visible_password = false;
	var visible_check_password = false;
	
	// メッセージを非表示
	var hideAlert = function() {
		$("#flash").hide();
	}
	setTimeout(hideAlert, 3000);
	
	$("#modal-open").click(function(){
		$("#confirm-name").text($("input[name='lastName']").val()+"　"+$("input[name='firstName']").val());
		$("#confirm-hired").text($("#hiredOn").val());
		$("#confirm-department").text( $("select[name='departmentId'] option:selected").text());
		$("#confirm-email").text($("input[name='email']").val()+$("select[name='domain'] option:selected").val());
	});
	
	
	$("input[name='lastName']").on("change", function(){
		var last_name = $(this).val().length
		if(last_name<1 || last_name>10 || $(this).val().match(/^[ 　\r\n\t]*$/) ){
			visible_last_name = false;
			$("#last-name-error").text("姓を入力してください");
		}else{
			visible_last_name = true;
			$("#last-name-error").text("");
			$(this).val($(this).val().replace(/\s+/g, ""))
		}
		button_status();
	});

	$("input[name='firstName']").on("change", function(){
		var first_name = $(this).val().length
		if(first_name<1 || first_name>10 || $(this).val().match(/^[ 　\r\n\t]*$/) ){
			visible_first_name = false;
			$("#first-name-error").text("名前を入力してください");
		}else{
			visible_first_name = true;
			$("#first-name-error").text("");
			$(this).val($(this).val().replace(/\s+/g, ""))
		}
		button_status();
	});

	$("input[name='hiredOn']").on("change", function(){
		if(!$(this).val().match(/\d{4}-\d{2}/) ){
			visible_hired = false;
			$("#hired-error").text("入社年月を選択してください");
		}else{
			visible_hired = true;
			$("#hired-error").text("");
		}
		button_status();
	});
	
	$("select[name='departmentId']").on("change", function(){
		if( $("select[name='departmentId'] option:selected").val()=="" ){
			visible_department = false;
			$("#department-error").text("所属を選択してください");
		}else{
			visible_department = true;
			$("#department-error").text("");
		}
		button_status();
	});
	
	$("input[name='email']").on("click", function(){
		visible_email = false;
		not_deplicated_email = false;
		button_status();
	});
	
	$("input[name='email']").on("keyup", function(){
		var email = $(this).val().length
		if(!$(this).val().match(/^[a-zA-Z.0-9]{1,30}$/) ){
			visible_email = false;
			$("#email-error2").text("@以前を入力してください ※記号は半角ドットのみ入力可");
		}else{
			visible_email = true;
			$("#email-error2").text("");
			$(this).val($(this).val().replace(/\s+/g, ""))
		}
			button_status();
	});
			
	$("input[name='email']").blur(function(){
				var hostUrl = location.href.replace("/register","") +"/email-check-api"
				var email = $(this).val()+$("select[name='domain'] option:selected").val();
				console.log(email);
				$.ajax({
					url: hostUrl,
					type: "GET",
					dataType: "json",
					data:{
						email: email ,
					},
					async: true
				}).done(function(data){
					if(data.duplicateEmail != null){
						not_deplicated_email = false;
						$("#email-error1").text(data.duplicateEmail);
					}else{
						not_deplicated_email = true;
						$("#email-error1").text("");
					}
					button_status();
				})
	});
	

	
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
		if(visible_last_name && visible_first_name && visible_hired && visible_department && visible_email && not_deplicated_email && visible_password && visible_check_password){
			$("#modal-open").prop("disabled",false);
			$("#check-datas-error").text("");
		}else{
			$("#modal-open").prop("disabled",true);
			$("#check-datas-error").text("未入力または不正な入力項目があります");
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
