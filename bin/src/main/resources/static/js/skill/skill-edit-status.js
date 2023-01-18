$(function(){
	//popoverを使用するために記載
	$('[data-toggle="popover"]').popover();
	//一時保存は活性化
	$('.temporary-button').prop('disabled', false);
	//一度一時保存ボタンを押下後、非活性化にする
	$('.temporary-button').on('click', function(){
		$(this).prop('disabled', true);
		$('#stage').attr('value', "1");
		//一時保存する場合はvalidationcheckなし
		$('input').removeClass();
		$('textarea').removeClass();
		$('.edit').submit();
	});
	
	//所持している性格は黄色で表示させる
	$(".personality-skill").ready(function(){
		let url = location.href;
		let replacedUrl;
		if(url.includes('/do')){
			replacedUrl = url.replace('/skill/status/edit/do', '/get_had_personality');				
		}else{
			replacedUrl = url.replace('/skill/status/edit', '/get_had_personality');						
		}
		$.ajax({
			url : replacedUrl,
			type : "GET",
			dataType : "json",
			data : {
				statusId : $("#status-id").val()
			},
			async : true
		}).done(function(data){
			//全ての性格リストを取得.
			const personalitySkillList = document.getElementsByClassName('personality-skill');
			if(data.hadPersonalityList !== null){
				data.hadPersonalityList.forEach((hadPersonality) =>{
					let idStr = hadPersonality.personalityId;
					$('label[for=personality'+idStr+']').css('background-color', 'Yellow');
					$('label[for=personality'+idStr+']').removeClass().addClass("personality-skill active");
					$('input[id=personality' +idStr+']').prop('checked', true);
				});
				
				let adminCommentLength;
				let userCommentLength;
				
				if($("#userComment").val() === undefined){
		 			adminCommentLength = $('#adminComment').val().replace(/[\n\s　]/g, "").length;			
		 		}else{
		 			userCommentLength = $('#userComment').val().replace(/[\n\s　]/g, "").length;			
		 		}
		         
		         if($('label').hasClass('personality-skill active') && (adminCommentLength || userCommentLength) > 0){
		        	 isEngineerSkillScore()
		        }else{
		        	isModalButton(false);
		        }
		         
			}else{
				$('#modal-open').prop('disabled', true);
			}
			
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			alert("エラーが発生しました!");
			console.log("XMLHttpRequest:" + XMLHttpRequest.status);
			console.log("testStatus:" + textStatus);
			console.log("errorThrown:" + errorThrown.message);
		});
	});
	
	//性格の変更を可能にする
	$('.personality-skill').on('change',function(){
		let idStr = $(this).attr('id');
		let p =document.getElementById(idStr);
		let status=$(this).prop('checked');
		if(status==true){
			$('label[for='+idStr+']').css('background-color', 'Yellow');
			$('label[for='+idStr+']').removeClass().addClass("personality-skill active");
		}else{
			$('label[for='+idStr+']').css('background-color', '#EEEEEE');
			$('label[for='+idStr+']').removeClass().addClass("personality-skill");
		}
		
		let adminCommentLength;
		let userCommentLength;
		
		if($("#userComment").val() === undefined){
			adminCommentLength = $('#adminComment').val().replace(/[\n\s　]/g, "").length;			
		}else{
			userCommentLength = $('#userComment').val().replace(/[\n\s　]/g, "").length;			
		}
		
		//性格が1つの選択されていなければ申請ボタンを非活性化
		if($('label').hasClass('personality-skill active') && (adminCommentLength || userCommentLength) > 0){
			isEngineerSkillScore()
		}else{
			isModalButton(false);
		}
	});
	
	// JQuery-validation-engineの設定
	jQuery(".edit").validationEngine({
		scrollOffset: 70
	});
	
	//エンジニア技術スコアを入力した際に作動
	 $('.engineer-skill-score').on('input', function() {
         let value = $(this).val();
         //01,02等のパターン
         const numberPattern = /^[0]{1}\d{1}$/i;
         //a,あ,!等の数字以外のパターン
         const nonNumberPattern = /[^0-9]+/i;
     
         //a,あ,!等の数字以外を入力したら空に置き換える
         if(value.match(nonNumberPattern)){
        	 this.value = value.replace( /[^0-9]/g , "" );
         }
       　//101以上を入力したら100にする
         if(100 < value){
        	 this.value = value.replace(value, 100);
         }
         //0を入力した後次に数字を入力できないようにする
         if(this.value.match(numberPattern)){
        	 this.value = value.replace(value, 0);
         }
         
        let adminCommentLength;
 		let userCommentLength;
         
         if($("#userComment").val() === undefined){
 			adminCommentLength = $('#adminComment').val().replace(/[\n\s　]/g, "").length;			
 		}else{
 			userCommentLength = $('#userComment').val().replace(/[\n\s　]/g, "").length;			
 		}
         
         if($('label').hasClass('personality-skill active') && (adminCommentLength || userCommentLength) > 0){
        	 isEngineerSkillScore()
        }else{
        	isModalButton(false);
        }
     });
	 
	 //管理者コメントが1文字も入力されていなければ申請ボタン非活性化
	 $('#adminComment').on("keydown keyup keypress change", function() {
		//スペースと改行を無視した文字数
		let len = $('#adminComment').val().replace(/[\n\s　]/g, "").length;
		if (len > 0 && $('label').hasClass('personality-skill active')) {
			isEngineerSkillScore()
		} else {
			isModalButton(false);
		}
	});
	
	 //ユーザーコメントが1文字も入力されていなければ申請ボタン非活性化
	 $('#userComment').on("keydown keyup keypress change", function() {
		 //スペースと改行を無視した文字数
		 let len = $('#userComment').val().replace(/[\n\s　]/g, "").length;
		 if (len > 0 && $('label').hasClass('personality-skill active')) {
			 isEngineerSkillScore()
		 } else {
			 isModalButton(false);
		 }
	 });
	 
	 //モーダルボタンの活性,非活性化 
	 const isModalButton = (isModalOpen) => {
		 if(isModalOpen){
			 $("#modal-open").prop("disabled", false);			 
		 }else{
			 $("#modal-open").prop("disabled", true);			 
		 }
	 };
	 
	 //エンジニアスキル点数チェック
	 const isEngineerSkillScore = () => {
		 $(".engineer-skill-score").each(function(){
        	 if($(this).val() === '' || $(this).val() > 101 || 0 > $(this).val()){
        		 isModalButton(false);
        		 return false;
        	 }else{
	       		 isModalButton(true);		       		 
	       	 }
         });
	 }
	 
	// Enterキーによるサブミットを防止
	$('input').on("keypress",function(e){
		if (e.which === 13) {
			return false;
		}
	});
	
	const fadeOutAlert = function(){
		$("#flash").fadeOut("slow");
	}
	setTimeout(fadeOutAlert, 3000);
});