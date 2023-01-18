$(function(){
	$("#basicSkillChart").ready(function(){
		let url = location.href;
		let replacedUrl;
		if(url.includes('/skill')){
			replacedUrl = url.replace('/skill/status', '/get_had_basic_skill');			
		}else{
			replacedUrl = url.replace('/request/status', '/get_had_basic_skill');
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
			let names = [];
			let scores = [];
			if(data.hadBasicSkillList !== null){
				data.hadBasicSkillList.forEach((hadBasicSkill) => {
					names.push(hadBasicSkill.basicSkill.name);
					scores.push(hadBasicSkill.score);
				});
	     		let ctx = document.getElementById('basicSkillChart');
				Chart.defaults.global.defaultFontSize = 16;
				let myRadarChart = new Chart(ctx, {
					type : 'radar',
					data : {
						labels : names,
						datasets : [{
							label : "現状",
							data : scores,
							borderColor: 'rgba(255, 0, 0, 0.7)',
							backgroundColor: 'rgba(255, 0, 0, 0.2)'
						}]
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
						},
						plugins:{
							font: function(context) {
						          let width = context.chart.width;
						          let size = Math.round(width / 32);
						           return {
						             size: size,
						            weight: 600
						          };
						    },
						}
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


