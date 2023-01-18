$(function(){
	
	// 送信ボタン１回押下したらボタン無効化する
	$('#done').on('click',function(){
		$(this).prop('disabled',true);
		$('#editUserForm').submit();
	});
	
		// メッセージを非表示
		var hideAlert1 = function() {
			$("#flash1").hide();
		}
		var hideAlert2 = function() {
			$("#flash2").hide();
		}
		setTimeout(hideAlert1, 3000);
		setTimeout(hideAlert2, 3000);

	// 入力チェック用
	$("#modal-open").prop("disabled",true);
	$("#check-datas-error").text("変更箇所がないため登録できません");
	var visible_last_name = true;
	var visible_first_name = true;
	var visible_hired = true;
	var visible_department = true;
	var visible_email = true;
	
	var not_changed_last_name = true;
	var not_changed_first_name = true;
	var not_changed_hired = true;
	var not_changed_department = true;
	var not_changed_email = true;
	
	$("#modal-open").click(function(){
		$("#confirm-name").text($("input[name='lastName']").val()+"　"+$("input[name='firstName']").val());
		$("#confirm-hired").text($("#hiredOn").val());
		$("#confirm-department").text( $("select[name='departmentId'] option:selected").text());
		$("#confirm-email").text($("input[name='email']").val());
	});
	
	
	$("input[name='lastName']").on("change", function(){
		var last_name = $(this).val().replace(/\s+/g, "");
		var last_name_length = last_name.length;
		if(last_name_length<1 || last_name_length>10 || last_name.match(/^[ 　\r\n\t]*$/) ){
			visible_last_name = false;
			$("#last-name-error").text("姓を入力してください");
		}else{
			visible_last_name = true;
			$("#last-name-error").text("");
		}
		$(this).val($(this).val().replace(/\s+/g, ""))
		
		if(last_name == $('#origin_last_name').val()){
			not_changed_last_name = true;
		}else{
			not_changed_last_name = false;
		}

		button_status();
	});

	$("input[name='firstName']").on("change", function(){
		var first_name =$(this).val().replace(/\s+/g, "");
		var first_name_length = first_name.length;
		if(first_name_length<1 || first_name_length>10 || first_name.match(/^[ 　\r\n\t]*$/) ){
			visible_first_name = false;
			$("#first-name-error").text("名前を入力してください");
		}else{
			visible_first_name = true;
			$("#first-name-error").text("");
		}
		$(this).val($(this).val().replace(/\s+/g, ""))

		if(first_name == $('#origin_first_name').val()){
			not_changed_first_name = true;
		}else{
			not_changed_first_name = false;
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

		if($(this).val() == $('#origin_hired_on').val()){
			not_changed_hired = true;
		}else{
			not_changed_hired = false;
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
		
		if($("select[name='departmentId'] option:selected").val() == $('#origin_department_id').val()){
			not_changed_department = true;
		}else{
			not_changed_department = false;
		}

		button_status();
	});
	
	$("input[name='email']").on("keyup", function(){
		if(!$(this).val().match(/^[a-zA-Z.0-9@]{3,50}$/) ){
			visible_email = false;
			$("#email-error").text("記号は半角ドット・@のみ入力可");
		}else{
			visible_email = true;
			$("#email-error").text("");
			$(this).val($(this).val().replace(/\s+/g, ""))
		}

		if($(this).val() == $('#origin_email').val()){
			not_changed_email = true;
		}else{
			not_changed_email = false;
		}
		
		button_status();
	});
	
	function button_status() {
		
		if(not_changed_last_name && not_changed_first_name && not_changed_hired && not_changed_department && not_changed_email){
			$("#modal-open").prop("disabled",true);
			$("#check-datas-error").text("変更箇所がないため登録できません");
		} else if(!(visible_last_name && visible_first_name && visible_hired && visible_department && visible_email)){
			$("#modal-open").prop("disabled",true);
			$("#check-datas-error").text("未入力または不正な入力項目があります");
		} else{
			$("#modal-open").prop("disabled",false);
			$("#check-datas-error").text("");
		}
	}

});
