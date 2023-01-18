$(function(){
	//popoverを使用可にする
	$('[data-toggle="popover"]').popover();
	$(".special-ability").ready(function(){
		let url = location.href;
		let replacedUrl;
		if(url.includes('/skill')){
			replacedUrl = url.replace('/skill/status', '/get_had_personality');			
		}else{
			replacedUrl = url.replace('/request/status', '/get_had_personality');
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
			let specialAbilities = $('.special-ability');
			if(specialAbilities.length !== 0){
				let personalityIdListInHadPersonalityList = [];
				let personalityTypeList = [];
				
				data.hadPersonalityList.forEach((hadPersonality) => {
					personalityIdListInHadPersonalityList.push(hadPersonality.personalityId);
				});
				
				data.hadPersonalityList.forEach((hadPersonality) => {
					personalityTypeList.push(hadPersonality.personality.type);
				});
				
				let validSpecialAbilities = [...specialAbilities].filter((specialAbility) => {
					return personalityIdListInHadPersonalityList.includes(Number(specialAbility.getAttribute('id')));
				});
				
				validSpecialAbilities.forEach((validSpecialAbility, index) => {
					let specialAbility = validSpecialAbility;
					specialAbility.setAttribute('data-toggle', 'popover');
					//specialAbilityの子要素を取得
					let child = specialAbility.firstElementChild;
					//文字を表示させる.
					child.style.visibility = '';
					if(personalityTypeList[index] === 'b'){
						specialAbility.className = 'special-ability positive-ability';	
					}else if(personalityTypeList[index] === 'r'){
						specialAbility.className = 'special-ability negative-ability';
					}else if(personalityTypeList[index] === 'p'){
						specialAbility.className = 'special-ability negaposi-ability';
					}else if(personalityTypeList[index] === 'g'){
						specialAbility.className = 'special-ability personality';
					}		
				});
			}
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			alert("エラーが発生しました!");
			console.log("XMLHttpRequest:" + XMLHttpRequest.status);
			console.log("testStatus:" + textStatus);
			console.log("errorThrown:" + errorThrown.message);
		});
	});

});