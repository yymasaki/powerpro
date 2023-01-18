$(function(){
	// 登録ボタンクリック時の処理
	$('#templateRegisterModalCenter').on('show.bs.modal',function(event){
		// 値を取得
		var departmentName = $('#departmentIdSelect option:selected').text();
		var templateName = $('#templateNameInput').val();
		// HTMLの書き換え
		$('#department-name').text(departmentName);
		$('#template-name').text(templateName);
		
		var engineerSkillScoreList = [];
		// engineerSkillScoreListに格納
		$('.inputEngineerSkill').each(function(i,o){
			engineerSkillScoreList.push($(o).val());
		});
		
		var departmentId = $('#departmentIdSelect option:selected').val();
		// ajaxでエンジニア技術名を取得し、HTMLを書き換える
		var url = location.href;
		var index = url.indexOf("/do");
		
		if (index !== -1) {
			url = url.substring(0,url.indexOf("/do"));
		}
		
		$.ajax({
			url : url + "/get_enginner_skill_list",
			type : "GET",
			dataType : "json",
			data : {
				departmentId : departmentId
			},
			async : true			
		}).done(function(data){
			var engineerSkillAndScoreHtml = '<br>-----------------<br><br>';
			for (let i = 0; i < data.engineerSkillList.length; i++) {
				let engineerSkillName = data.engineerSkillList[i].name;
				let engineerSkillScore = engineerSkillScoreList[i];
				engineerSkillAndScoreHtml += '<h6>' + engineerSkillName  + ':<span>' + engineerSkillScore + '</span>' + '</h6>'
			}
			engineerSkillAndScoreHtml += '-----------------';
			$('#engineer-skill').html(engineerSkillAndScoreHtml);
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			alert("エラーが発生しました!");
			console.log("XMLHttpRequest:" + XMLHttpRequest.status);
			console.log("testStatus:" + textStatus);
			console.log("errorThrown:" + errorThrown.message);		
		});
		
		var basicSkillScoreList = [];
		// basicSkillScoreListに格納
		$('.inputBasicSkill').each(function(i,o){
			basicSkillScoreList.push($(o).val());
		});
		
		// ajaxで基本スキルを取得し、HTMLを書き換える
		$.ajax({
			url : url + "/get_basic_skill_list",
			type : "GET",
			dataType : "json",
			data : {
				departmentId : departmentId
			},
			async : true			
		}).done(function(data){
			var basicSkillAndScoreHtml = '<br>-----------------<br><br>';
			for (let i = 0; i < data.basicSkillList.length; i++) {
				let basicSkillName = data.basicSkillList[i].name;
				let basicSkillScore = basicSkillScoreList[i];
				basicSkillAndScoreHtml += '<h6>' + basicSkillName + ':<span>' + basicSkillScore + '</span>' + '</h6>'
			}
			basicSkillAndScoreHtml += '-----------------';
			$('#basic-skill').html(basicSkillAndScoreHtml);
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			alert("エラーが発生しました!");
			console.log("XMLHttpRequest:" + XMLHttpRequest.status);
			console.log("testStatus:" + textStatus);
			console.log("errorThrown:" + errorThrown.message);		
		});
	});
	
	$('#done').on('click',function(){
		$(this).prop('disabled',true);
		$('#registerTemplateForm').submit();
	});
});