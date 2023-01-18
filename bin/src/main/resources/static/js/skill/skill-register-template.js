$(function(){
	var departmentId = $('#departmentIdSelect option:selected').val();
	createEngineerSkillList(departmentId);
	createBasicSkillList(departmentId);
	
	// 初期状態では登録ボタンを無効化する
	$('#registerButton').prop("disabled",true);
	
	// JQuery-validation-engineの設定
	jQuery("#registerTemplateForm").validationEngine({
		scrollOffset: 70
	});
	
	// Enterキーによるサブミットを防止
	$('#registerTemplateForm').on("keypress",function(e){
		if (e.which === 13) {
			return false;
		}
	});	
	
	// 所属区分が変更されたら、エンジニア技術と基本スキルの項目名を変更する
	$('#departmentIdSelect').on("change",function(){
		departmentId = $('#departmentIdSelect option:selected').val();
		createEngineerSkillList(departmentId);
		createBasicSkillList(departmentId);
	});
	
	// エンジニア技術フォームを動的に生成する
	function createEngineerSkillList(departmentId){
		let url = location.href;
		let index = url.indexOf("/do");
		
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
			var engineerSkillNameListHtml = '';
			for (let i = 0; i < data.engineerSkillList.length; i++) {
				let engineerSkillName = data.engineerSkillList[i].name;
				let engineerSkillId = data.engineerSkillList[i].engineerSkillId;
				engineerSkillNameListHtml += '<div class="contents skill-point">' +
							'<label class="engineer-skill-name">'+ engineerSkillName + '</label>' +
							'<input type="hidden" name="engineerSkillIdList" value="' + engineerSkillId + '">' +
							'<input type="number" class="inputEngineerSkill skill-value validate[required,custom[integer],max[100],min[0]]" name="templateEngineerSkillScoreList"' +
							'min="0" max="100" value="100"></div>' 
			}
			$('#engineerSkillNameList').html(engineerSkillNameListHtml);
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			alert("エラーが発生しました!");
			console.log("XMLHttpRequest:" + XMLHttpRequest.status);
			console.log("testStatus:" + textStatus);
			console.log("errorThrown:" + errorThrown.message);		
		});
	};
	
	// 基本スキルフォームを動的に生成する
	function createBasicSkillList(departmentId){
		let url = location.href;
		let index = url.indexOf("/do");
		
		if (index !== -1) {
			url = url.substring(0,url.indexOf("/do"));
		}
		
		$.ajax({
			url : url + "/get_basic_skill_list",
			type : "GET",
			dataType : "json",
			data : {
				departmentId : departmentId
			},
			async : true
		}).done(function(data){
			var basicSkillNameListHtml = '';
			for (let i = 0; i < data.basicSkillList.length; i++) {
				let basicSkillName = data.basicSkillList[i].name;
				let basicSkillId = data.basicSkillList[i].basicSkillId;
				basicSkillNameListHtml += '<div class="contents skill-point">' +
								'<label>'+ basicSkillName + '</label>' +
								'<input type="hidden" name="basicSkillIdList" value="' + basicSkillId + '">' +
								'<input type="number" class="inputBasicSkill skill-value validate[required,custom[integer],max[5],min[1]]" name="templateBasicSkillScoreList"'+
								'min="1" max="5" value="5"></div>'
				
			}
			$('#basicSkillNameList').html(basicSkillNameListHtml);
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			alert("エラーが発生しました!");
			console.log("XMLHttpRequest:" + XMLHttpRequest.status);
			console.log("testStatus:" + textStatus);
			console.log("errorThrown:" + errorThrown.message);		
		});
	};
	
	// 登録ボタンの活性 / 非活性の判定に用いる
	var isTemplateNameValid = false;
	var isEngineerSkillScoreListValid = false;
	var isBasicSkillScoreListValid = false;
	
	// テンプレート名に関する判定を行う
	function judgeTemplateName () {
		let url = location.href;
		let index = url.indexOf("/do");
		if (index !== -1) {
			url = url.substring(0,url.indexOf("/do"));
		}
		let templateName = $('#templateNameInput').val();
		let pattern = /^[ 　]+$/;
		let isSpace = pattern.test(templateName);
		
		$.ajax({
			url : url + "/check_duplicate",
			type : "GET",
			dataType : "json",
			data : {
				departmentId : departmentId,
				name : templateName				
			},
			async : true
		}).done(function(data){
			let message = data.message;
			if (templateName === '' || isSpace) {
				$('#ok-message').hide();
				$('#ng-message').hide();
				$('#error-message').show();
				isTemplateNameValid = false;
			} else if (message === 'このテンプレート名は有効です') {
				$('#ok-message').show();
				$('#ok-message').html(message);
				$('#ng-message').hide();
				$('#error-message').hide();
				isTemplateNameValid = true;
			} else if (message === 'このテンプレート名は既に使用されています') {
				$('#ng-message').show();
				$('#ng-message').html(message);
				$('#ok-message').hide();
				$('#error-message').hide();
				isTemplateNameValid = false;
			}
			judgeEngineerSkill ();
			judgeBasicSkill ();
			judgeRegister();
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			alert("エラーが発生しました!");
			console.log("XMLHttpRequest:" + XMLHttpRequest.status);
			console.log("testStatus:" + textStatus);
			console.log("errorThrown:" + errorThrown.message);		
		});
	}
	
	// エンジニア技術の判定を行う
	function judgeEngineerSkill () {
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
		
		if (countOfValidInputTemplateEngineerSkillScore == 7) {
			isEngineerSkillScoreListValid = true;
		} else {
			isEngineerSkillScoreListValid = false;
		}
	}
	
	// 基本スキルの判定を行う
	function judgeBasicSkill() {
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
		
		if (countOfValidInputTemplateBasicSkillScore == 6) {
			isBasicSkillScoreListValid = true;
		} else {
			isBasicSkillScoreListValid = false;
		}
	}
	
	// テンプレート名が入力された際の処理
	$('#templateNameInput').on('keyup',function(){
		judgeTemplateName ();
	});
	
	// エンジニア技術が入力された際の処理
	$(document).on('keyup mouseup','.inputEngineerSkill',function(){
		judgeTemplateName ();
		judgeEngineerSkill ();
		judgeBasicSkill ();	
		judgeRegister();
	});
	
	// 基本スキルが入力された際の処理
	$(document).on('keyup mouseup','.inputBasicSkill',function(){
		judgeTemplateName ();
		judgeEngineerSkill ();
		judgeBasicSkill ();
		judgeRegister();
	});
	
	// 登録ボタンの活性/非活性の管理
	function judgeRegister(){
		if (isTemplateNameValid == true && isEngineerSkillScoreListValid == true && isBasicSkillScoreListValid == true) {
			$('#registerButton').prop("disabled",false);
		} else {
			$('#registerButton').prop("disabled",true);
		}
	};
});