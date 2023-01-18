$(function(){
	var templateId = $('#template-id').val();
	
	if (templateId != '') {
		getTemplateBasicSkillList();
	}

	function getTemplateBasicSkillList(){
		var url = location.href;
		let index = url.indexOf("?");
		if (index !== -1) {
			url = url.substring(0,url.indexOf("?"));
		}
		$.ajax({
			url : url + "/get_template_basic_skill_list",
			type : "GET",
			dataType : "json",
			data : {
				templateId : templateId
			},
			async : true		
		}).done(function(data){
			let names = [];
			let scores = [];
			for (let templateBasicSkill of data.templateBasicSkillList) {
				names.push(templateBasicSkill.basicSkill.name);
				scores.push(templateBasicSkill.score);
			}
			const ctx = document.getElementById("templateChart");
			Chart.defaults.global.defaultFontSize = 16;
			const templateChart = new Chart(ctx,{
				type : 'radar',
				data : {
					labels : names,
					datasets : [ {
						label : "現状",
						data : scores,
						borderColor : 'rgba(0, 0, 255, 0.7)',
						backgroundColor: 'rgba(0, 0, 255, 0.2)'
					}],
				},
				options : {
					scale : {
						pointLabels : {
							fontSize : 14
						},
						ticks : {
							suggestedMin: 0,
		                    suggestedMax: 5,
		                    stepSize: 1
						}
					}
				}
			});
		}).fail(function(XMLHttpRequest, textStatus, errorThrown){
			alert("エラーが発生しました!");
			console.log("XMLHttpRequest:" + XMLHttpRequest.status);
			console.log("testStatus:" + textStatus);
			console.log("errorThrown:" + errorThrown.message);		
		});
	}
	
	
	

	
	
	
});


