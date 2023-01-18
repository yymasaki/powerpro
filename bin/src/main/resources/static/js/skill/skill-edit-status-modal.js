$(function() {

	//ローディング画面の設定
	var stopload= function(){
		$('.loading').hide();	
	}
	stopload();
	
	// 送信ボタンをクリック時の処理
	$('#exampleModal').on('show.bs.modal', function(event) {
		let pcName = $('#pc option:selected').text();
		let mobileName = $('#mobile option:selected').text();
		let userComment = $('#userComment').val();
		let adminComment = $('#adminComment').val();
		
		let engineerSkillNameList = [];
		$('.engineer-skill-name').each(function(){
			engineerSkillNameList.push($(this).text());
		});
		
		let engineerSkillScoreList = [];
		$('.engineer-skill-score').each(function(){
			engineerSkillScoreList.push($(this).val());
		});
		
		let basicSkillNameList = [];
		$('.basic-skill-name').each(function(){
			basicSkillNameList.push($(this).text());
		});
		
		let basicSkillScoreList = [];
		$('.basic-skill-score').each(function(){
			basicSkillScoreList.push($(this).val());
		});
		
		let personalitySkillList = [];
		$('label[class="personality-skill active"]').each(function(){
			personalitySkillList.push($(this).text());
		});
		
		dataToHtml(pcName, mobileName, userComment, adminComment, engineerSkillNameList, engineerSkillScoreList, basicSkillNameList, basicSkillScoreList, personalitySkillList);
		
		let button = $(event.relatedTarget); 
		let recipient = button.data('whatever'); 
		let modal = $(this);
		// 確定ボタンに処理名を表示
		if (recipient === "approval") {
			$('#stage').attr('value', "0");
			modal.find('#edit').text("更新");
			$('#sentense').text("更新");
		} else if (recipient === "application") {
			$('#stage').attr('value', "2");
			modal.find('#edit').text("申請");
			$('#sentense').text("申請");
		} 
		
		//一度承認ボタンを押下後、非活性化にする.処理中を表示.
		$('.modal-button').on('click', function(){
			$('.loading').show();
			setTimeout(stopload, 20000);
			$(this).prop('disabled', true);
			$('.edit').submit();
		});
	})
	
	//データをHTMLに反映させる.
	const dataToHtml = (pcName, mobileName, userComment, adminComment, engineerSkillNameList, engineerSkillScoreList, basicSkillNameList, basicSkillScoreList, personalitySkillList) => {
		$('#pc-name').text(pcName);
		$('#mobile-name').text(mobileName);
		if(userComment === undefined){
			$('#admin-comment').html(adminComment.replace(/\r?\n/g,'<br>'));			
		}else if(adminComment === undefined){
			$('#user-comment').html(userComment.replace(/\r?\n/g,'<br>'));
		}
		removeChild();
		
		engineerSkillScoreList.forEach((engineerSkillScore, index) => {
			$('#engineer-skill-list').append('<h6>' + engineerSkillNameList[index] + ":" + engineerSkillScoreList[index] + '</h6>');
		});
		
		basicSkillScoreList.forEach((basicSkillScore, index) => {
			$('#basic-skill-list').append('<h6>' + basicSkillNameList[index] + ":" + basicSkillScoreList[index] + '</h6>');
		})
		
		personalitySkillList.forEach((personalitySkill) => {
			$('#personality').append(personalitySkill + '&nbsp;' );
		});
	};
	
	//エンジニア,基本スキルの子要素を削除する
	const removeChild = () =>{
		$('#personality').empty();
		$('#engineer-skill-list').empty();
		$('#basic-skill-list').empty();
	};

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
});