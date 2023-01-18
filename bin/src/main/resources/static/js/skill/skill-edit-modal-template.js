$(function(){
	// 編集完了ボタンクリック時の処理
	$('#templateEditModalCenter').on('show.bs.modal',function(event){
		var templateName = $('#templateNameInput').val();
		$('#template-name').text(templateName);
		
		var templateEngineerSkillNameList = [];
		var templateEngineerSkillScoreList = [];
		var templateBasicSkillNameList = [];
		var templateBasicSkillScoreList = [];
		
		$('.engineer-skill-name').each(function(i,o){
			templateEngineerSkillNameList.push($(o).text());
		});
		
		$('.inputEngineerSkill').each(function(i,o){
			templateEngineerSkillScoreList.push($(o).val());
		});
		
		$('.basic-skill-name').each(function(i,o){
			templateBasicSkillNameList.push($(o).text());
		});
		
		$('.inputBasicSkill').each(function(i,o){
			templateBasicSkillScoreList.push($(o).val());
		});
		// HTMLを編集
		// エンジニア技術
		var templateEngineerSkillAndScoreHtml = '<br>-----------------<br><br>';
		for (let i = 0; i < templateEngineerSkillNameList.length; i++) {
			let templateEngineerSkillName = templateEngineerSkillNameList[i];
			let templateEngineerSkillScore = templateEngineerSkillScoreList[i];
			templateEngineerSkillAndScoreHtml += 
				'<h6>' + templateEngineerSkillName + ':<span>' + templateEngineerSkillScore + '</span></h6>' 
		}
		templateEngineerSkillAndScoreHtml += '-----------------';
		$('#engineer-skill').html(templateEngineerSkillAndScoreHtml);
		// 基本スキル
		var templateBasicSkillAndScoreHtml = '<br>-----------------<br><br>';
		for (let i = 0; i < templateBasicSkillNameList.length; i++) {
			let templateBasicSkillName = templateBasicSkillNameList[i];
			let templateBasicSkillScore = templateBasicSkillScoreList[i];
			templateBasicSkillAndScoreHtml += 
				'<h6>' + templateBasicSkillName + ':<span>' + templateBasicSkillScore + '</span></h6>'
		}
		templateBasicSkillAndScoreHtml += '-----------------';
		$('#basic-skill').html(templateBasicSkillAndScoreHtml);
	});
	
	$('#done').on('click',function(){
		$(this).prop('disabled',true);
		$('#editTemplateForm').submit();
	});
});