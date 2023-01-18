$(function(){
	// テンプレート名をセット
	var currentTemplateName = $('#current-template-name').val();
	$('#templateNameInput').val(currentTemplateName);
	
	// エンジニア技術の表示
	var templateEngineerSkillIdList = [];
	var templateEngineerSkillNameList = [];
	var templateEngineerSkillScoreList = [];
	
	$('.templateEngineerSkillIdList').each(function(i,o){
		templateEngineerSkillIdList.push($(o).val());
	});
	
	$('.templateEngineerSkillNameList').each(function(i,o){
		templateEngineerSkillNameList.push($(o).val());
	});
	
	$('.templateEngineerSkillScoreList').each(function(i,o){
		templateEngineerSkillScoreList.push($(o).val());
	});

	var templateEngineerSkillEditFormHtml = '';
	for (let i = 0; i < templateEngineerSkillIdList.length; i++) {
		let templateEngineerSkillId = templateEngineerSkillIdList[i];
		let templateEngineerSkillName = templateEngineerSkillNameList[i];
		let templateEngineerSkillScore = templateEngineerSkillScoreList[i];
		templateEngineerSkillEditFormHtml += '<div class="contents skill-point">' + 
							'<label class="engineer-skill-name">' + templateEngineerSkillName + '</label>' +
							'<input type="hidden" name="engineerSkillIdList" value="' + templateEngineerSkillId + '">' +
							'<input type="number" name="templateEngineerSkillScoreList" class="inputEngineerSkill skill-value validate[required,custom[integer],max[100],min[0]]" ' +
							'min="0" max="100" value="'+ templateEngineerSkillScore + '"></div>' 		
	}
	$('#engineerSkillNameList').html(templateEngineerSkillEditFormHtml);
	
	// 基本スキルの表示
	var templateBasicSkillIdList = [];
	var templateBasicSkillNameList = [];
	var templateBasicSkillScoreList = [];
	
	$('.templateBasicSkillIdList').each(function(i,o){
		templateBasicSkillIdList.push($(o).val());
	});
	
	$('.templateBasicSkillNameList').each(function(i,o){
		templateBasicSkillNameList.push($(o).val());
	});	
	
	$('.templateBasicSkillScoreList').each(function(i,o){
		templateBasicSkillScoreList.push($(o).val());
	});
	
	var templateBasicSkillEditFormHtml = '';
	for (let i = 0; i < templateBasicSkillIdList.length; i++) {
		let templateBasicSkillId = templateBasicSkillIdList[i];
		let templateBasicSkillName = templateBasicSkillNameList[i];
		let templateBasicSkillScore = templateBasicSkillScoreList[i];
		templateBasicSkillEditFormHtml += '<div class="contents skill-point">' +
							'<label class="basic-skill-name">' + templateBasicSkillName + '</label>' +
							'<input type="hidden" name="basicSkillIdList" value="' + templateBasicSkillId + '">' +
							'<input type="number" class="inputBasicSkill skill-value validate[required,custom[integer],max[5],min[1]]" name="templateBasicSkillScoreList"'+
							'min="1" max="5" value="' + templateBasicSkillScore + '"></div>'
	}
	$('#basicSkillNameList').html(templateBasicSkillEditFormHtml);
	
	// 編集完了ボタンの活性/非活性の判定を行う
	function judgeEdit(){
		// 変更内容が1つもないかどうかの判断に必要な設定
		let templateName = $('#templateNameInput').val();
		let length = templateName.length;
		
		// テンプレート名が全て「半角スペース or 全角スペース」のみで構成されているかどうかを判定
		let pattern = /^[ 　]+$/;
		let isSpace = pattern.test(templateName);
		
		
		let inputTemplateEngineerSkillScoreList;
		inputTemplateEngineerSkillScoreList = [];
		
		$('.inputEngineerSkill').each(function(i,o){
			inputTemplateEngineerSkillScoreList.push($(o).val());
		});
		
		// 正常なエンジニア技術の数をカウントする（7つが正常）
		var countOfValidInputTemplateEngineerSkillScore = 0;
		for (let i = 0; i < inputTemplateEngineerSkillScoreList.length; i++) {
			let inputTemplateEngineerSkillScore = Number(inputTemplateEngineerSkillScoreList[i]);
			
			if (inputTemplateEngineerSkillScore != "") {
				if (Number.isInteger(inputTemplateEngineerSkillScore) && (0 <= inputTemplateEngineerSkillScore && inputTemplateEngineerSkillScore <= 100)) {
					countOfValidInputTemplateEngineerSkillScore++;
				}
			}
		}
		
		let inputTemplateBasicSkillScoreList;
		inputTemplateBasicSkillScoreList = [];
		
		$('.inputBasicSkill').each(function(i,o){
			inputTemplateBasicSkillScoreList.push($(o).val());
		});
		
		// 正常な基本スキルの数をカウントする（6つが正常）
		var countOfValidInputTemplateBasicSkillScore = 0;
		for (let i = 0; i < inputTemplateBasicSkillScoreList.length; i++) {
			let inputTemplateBasicSkillScore = Number(inputTemplateBasicSkillScoreList[i]);
			
			// 見やすいようにif文をネストする
			if (inputTemplateBasicSkillScore != "") {
				if (Number.isInteger(inputTemplateBasicSkillScore) && (1 <= inputTemplateBasicSkillScore && inputTemplateBasicSkillScore <= 5)) {
					countOfValidInputTemplateBasicSkillScore++;
				}
			}
		}
		
		let isTemplateNameNoChange;
		let isTemplateEngineerSkillScoreNoChange;
		let isTemplateBasicSkillScoreNoChange;
		
		// テンプレート名の比較
		if (currentTemplateName === templateName) {
			isTemplateNameNoChange = true;
		} else {
			isTemplateNameNoChange = false;
		}
		
		// エンジニアスキルスコアの比較
		if (JSON.stringify(templateEngineerSkillScoreList) === JSON.stringify(inputTemplateEngineerSkillScoreList)) {
			isTemplateEngineerSkillScoreNoChange = true;
		} else {
			isTemplateEngineerSkillScoreNoChange = false;
		}
		
		// 基本スキルの比較
		if (JSON.stringify(templateBasicSkillScoreList) === JSON.stringify(inputTemplateBasicSkillScoreList)) {
			isTemplateBasicSkillScoreNoChange = true;
		} else {
			isTemplateBasicSkillScoreNoChange = false;
		}
		
		// テンプレート名重複確認に必要な設定
		let url = location.href;
		let index = url.indexOf("?");
		if (index !== -1) {
			url = url.substring(0,url.indexOf("?"));
		}
		
		let index_do = url.indexOf("/do");
		if (index_do !== -1) {
			url = url.substring(0,url.indexOf("/do"));
		}
		
		let templateId = $('input[name="templateId"]').val();
		let departmentId = $('input[name="departmentId"]').val();
		
		$.ajax({
			url : url + "/check_duplicate",
			type : "GET",
			dataType : "json",
			data : {
				templateId : templateId,
				departmentId : departmentId,
				name : templateName
			},
			async : true
		}).done(function(data){
			let message = data.message;
			
			// 変更内容が1つもない場合、編集完了ボタンを非活性にする
			if (isTemplateNameNoChange && isTemplateEngineerSkillScoreNoChange && isTemplateBasicSkillScoreNoChange) {
				$('#ok-message').hide();
				$('#ng-message').hide();
				$('#no-edit').show();
				$('#editButton').prop("disabled",true);
			} else if (templateName === '' || isSpace ){
				$('#ok-message').hide();
				$('#ng-message').hide();
				$('#no-edit').hide();
				$('#editButton').prop("disabled",true);
			} else if (length > 30) {
				$('#ok-message').hide();
				$('#ng-message').hide();
				$('#no-edit').hide();
				$('#editButton').prop("disabled",true);
			} else if (message === 'このテンプレート名は既に使用されています') {
				$('#ng-message').show();
				$('#ng-message').html(message);
				$('#ok-message').hide();
				$('#error-message').hide();
				$('#no-edit').hide();
				$('#editButton').prop("disabled",true);
			} else if (countOfValidInputTemplateEngineerSkillScore != 7) {
				$('#no-edit').hide();
				$('#editButton').prop("disabled",true);
			} else if (countOfValidInputTemplateBasicSkillScore != 6) {
				$('#no-edit').hide();
				$('#editButton').prop("disabled",true);
			} else if (message === 'このテンプレート名は有効です' && (currentTemplateName !== templateName)) {
				$('#ok-message').show();
				$('#ok-message').html(message);
				$('#ng-message').hide();
				$('#error-message').hide();
				$('#no-edit').hide();
				$('#editButton').prop("disabled",false);
			}  else {
				$('#no-edit').hide();
				$('#editButton').prop("disabled",false);
			}
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			alert("エラーが発生しました!");
			console.log("XMLHttpRequest:" + XMLHttpRequest.status);
			console.log("testStatus:" + textStatus);
			console.log("errorThrown:" + errorThrown.message);		
		});
	};
	
	// 1回目の表示
	judgeEdit();
	
	// JQuery-validation-engineの設定
	jQuery("#editTemplateForm").validationEngine({
		scrollOffset: 70
	});	
	
	// Enterキーによるサブミットを防止
	$('#editTemplateForm').on("keypress",function(e){
		if (e.which === 13) {
			return false;
		}
	});
	
	// 何かしらが入力された際に編集完了ボタンを活性/非活性の判断を行う
	$("input").on("input",function(){
		judgeEdit();
	});
})