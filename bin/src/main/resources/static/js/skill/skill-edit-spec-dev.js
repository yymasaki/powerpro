$(function() {
	var addCount = 1;
	var devIndex = $("tfoot:last input").val();

	//開発経験追加
	$('#addDevelopment').on('click',function() {
		var addDevNode = document.getElementById("devNode");
		var devTable = document.getElementById("devTable");
		var clone = addDevNode.cloneNode(true);
		$(".dev-content-table .dev-count").each(function(){
			$(this).text(parseInt($(this).text())+1);
		});
		clone.removeAttribute('id');
		clone.setAttribute('class', 't1 mx-auto dev-content-table added-table');
		console.log(clone);
		devTable.after(clone);

		var addIndex = parseInt(devIndex) + addCount;

		$(".added-table:first .startedOn").attr("name","devExperienceList["+addIndex+"].startedOn");
		$(".added-table:first .finishedOn").attr("name","devExperienceList["+addIndex+"].finishedOn");
		$(".added-table:first .projectName").attr("name","devExperienceList["+addIndex+"].projectName");
		$(".added-table:first .role").attr("name","devExperienceList["+addIndex+"].role");
		$(".added-table:first .teamMembers").attr("name","devExperienceList["+addIndex+"].teamMembers");
		$(".added-table:first .projectMembers").attr("name","devExperienceList["+addIndex+"].projectMembers");
		$(".added-table:first .projectDetails").attr("name","devExperienceList["+addIndex+"].projectDetails");
		$(".added-table:first .tasks").attr("name","devExperienceList["+addIndex+"].tasks");
		$(".added-table:first .acquired").attr("name","devExperienceList["+addIndex+"].acquired");
		$(".added-table:first .OS").attr("name","usedTechnicalSkillList["+addIndex+"][0]");
		$(".added-table:first .language").attr("name","usedTechnicalSkillList["+addIndex+"][1]");
		$(".added-table:first .framework").attr("name","usedTechnicalSkillList["+addIndex+"][2]");
		$(".added-table:first .library").attr("name","usedTechnicalSkillList["+addIndex+"][3]");
		$(".added-table:first .middleware").attr("name","usedTechnicalSkillList["+addIndex+"][4]");
		$(".added-table:first .tool").attr("name","usedTechnicalSkillList["+addIndex+"][5]");
		$(".added-table:first .process").attr("name","usedTechnicalSkillList["+addIndex+"][6]");

		$(".added-table:first ul.ui-widget-content").remove();

		addCount++;

		var OSSB = $("#OSSB").text();
		console.log(OSSB);
		$(".OS").tagit({
			availableTags: OSSB,
			placeholderText: 'Windows10 + [Enter]',
			showAutocompleteOnFocus: true,
			removeConfirmation: true,
			singleField: true
		});
		$(".language").tagit({
			availableTags: [ [(${languageSB})] ],
			placeholderText: 'Java8 + [Enter]',
			showAutocompleteOnFocus: true,
			removeConfirmation: true,
			singleField: true
		});
		$(".framework").tagit({
			availableTags: [ [(${frameworkSB})] ],
			placeholderText: 'SpringBoot + [Enter]',
			showAutocompleteOnFocus: true,
			removeConfirmation: true,
			singleField: true
		});
		$(".library").tagit({
			availableTags: [ [(${librarySB})] ],
			placeholderText: 'Vue.js + [Enter]',
			showAutocompleteOnFocus: true,
			removeConfirmation: true,
			singleField: true
		});
		$(".middleware").tagit({
			availableTags: [ [(${middlewareSB})] ],
			placeholderText: 'MySQL8.0 + [Enter]',
			showAutocompleteOnFocus: true,
			removeConfirmation: true,
			singleField: true
		});
		$(".tool").tagit({
			availableTags: [ [(${toolSB})] ],
			placeholderText: 'GitHub + [Enter]',
			showAutocompleteOnFocus: true,
			removeConfirmation: true,
			singleField: true
		});
		$(".process").tagit({
			availableTags: [ [(${processSB})] ],
			placeholderText: '開発 + [Enter]',
			showAutocompleteOnFocus: true,
			removeConfirmation: true,
			singleField: true
		});
	});


	//開発経験削除
	$(document).on("click", ".delete-dev", function(){
		var inputs = [];
		inputs.push($(this).parents(".dev-content-table").find(".startedOn").val());
		inputs.push($(this).parents(".dev-content-table").find(".finishedOn").val());
		inputs.push($(this).parents(".dev-content-table").find(".projectName").val());
		inputs.push($(this).parents(".dev-content-table").find(".role").val());
		inputs.push($(this).parents(".dev-content-table").find(".teamMembers").val());
		inputs.push($(this).parents(".dev-content-table").find(".projectMembers").val());
		inputs.push($(this).parents(".dev-content-table").find(".projectDetails").val());
		inputs.push($(this).parents(".dev-content-table").find(".tasks").val());
		inputs.push($(this).parents(".dev-content-table").find(".acquired").val());
		inputs.push($(this).parents(".dev-content-table").find(".dev-OS").val());
		inputs.push($(this).parents(".dev-content-table").find(".dev-language").val());
		inputs.push($(this).parents(".dev-content-table").find(".dev-framework").val());
		inputs.push($(this).parents(".dev-content-table").find(".dev-library").val());
		inputs.push($(this).parents(".dev-content-table").find(".dev-middleware").val());
		inputs.push($(this).parents(".dev-content-table").find(".dev-tool").val());
		inputs.push($(this).parents(".dev-content-table").find(".dev-process").val());
		var count = $(this).prev(".dev-count").text();
		var isBlank = inputs.every((e) => {
			return (e == '' || e === null)
		});
		var deleteTable = $(this).parents(".dev-content-table");
		if(!isBlank) {
			if(!window.confirm('入力済の項目があります。\nNo.' + count + 'の開発経験欄を削除しますか？')){
				return;
			}
		}
		if($(this).parents(".dev-content-table").hasClass("added-table")) {
			$(".dev-content-table").each(function(){
				var devCount = $(this).find(".dev-count").text();
				//削除する欄より下の欄はdev-countを1小さくする
				if(parseInt(count) < parseInt(devCount)) {
					$(this).find(".dev-count").text(parseInt(devCount)-1);
				}
				//削除する欄より上の欄はnameのindexを1小さくする
				if(parseInt(devCount) < parseInt(count)) {
					var newIndex = parseInt(devIndex) + addCount - parseInt(devCount);
					changeNameIndex($(this), newIndex);
				}
			});
			addCount--;
		}else {
			$(".dev-content-table").each(function(e){
				var devCount = $(this).find(".dev-count").text();
				console.log(devCount);
				//削除する欄より下の欄はdev-countとdevIndexとnameのindexを1小さくする
				if(parseInt(count) < parseInt(devCount)) {
					$(this).find(".dev-count").text(parseInt(devCount)-1);
					var tfootIndex = parseInt($(this).find("tfoot input").val());
					$(this).find("tfoot input").val(tfootIndex - 1);
					changeNameIndex($(this), (tfootIndex - 1));
				}
				//追加した開発経験欄の場合はnameのindexを1小さくする
				if($(this).hasClass("added-table")) {
					var newIndex = parseInt(devIndex) + addCount - parseInt(devCount) - 1;
					changeNameIndex($(this), newIndex);
				}
			});
			devIndex = $("tfoot:last input").val();
		}
		deleteTable.remove();
	});

	var changeNameIndex = function(baseElement, newIndex){
		baseElement.find(".startedOn").attr("name","devExperienceList["+newIndex+"].startedOn");
		baseElement.find(".finishedOn").attr("name","devExperienceList["+newIndex+"].finishedOn");
		baseElement.find(".projectName").attr("name","devExperienceList["+newIndex+"].projectName");
		baseElement.find(".role").attr("name","devExperienceList["+newIndex+"].role");
		baseElement.find(".teamMembers").attr("name","devExperienceList["+newIndex+"].teamMembers");
		baseElement.find(".projectMembers").attr("name","devExperienceList["+newIndex+"].projectMembers");
		baseElement.find(".projectDetails").attr("name","devExperienceList["+newIndex+"].projectDetails");
		baseElement.find(".tasks").attr("name","devExperienceList["+newIndex+"].tasks");
		baseElement.find(".acquired").attr("name","devExperienceList["+newIndex+"].acquired");
		baseElement.find(".OS").attr("name","usedTechnicalSkillList["+newIndex+"][0]");
		baseElement.find(".language").attr("name","usedTechnicalSkillList["+newIndex+"][1]");
		baseElement.find(".framework").attr("name","usedTechnicalSkillList["+newIndex+"][2]");
		baseElement.find(".library").attr("name","usedTechnicalSkillList["+newIndex+"][3]");
		baseElement.find(".middleware").attr("name","usedTechnicalSkillList["+newIndex+"][4]");
		baseElement.find(".tool").attr("name","usedTechnicalSkillList["+newIndex+"][5]");
		baseElement.find(".process").attr("name","usedTechnicalSkillList["+newIndex+"][6]");
	}


	//usedTechnicalSkillに入力したものをhadTechnicalSkillに反映
	$(".dev-OS").tagit({
		afterTagAdded: function(event, ui) {
			reflectSkill($(this), $("#OS"));
		}
	});
	$(".dev-language").tagit({
		afterTagAdded: function(event, ui) {
			reflectSkill($(this), $("#language"));
		}
	});
	$(".dev-framework").tagit({
		afterTagAdded: function(event, ui) {
			reflectSkill($(this), $("#framework"));
		}
	});
	$(".dev-library").tagit({
		afterTagAdded: function(event, ui) {
			reflectSkill($(this), $("#library"));
		}
	});
	$(".dev-middleware").tagit({
		afterTagAdded: function(event, ui) {
			reflectSkill($(this), $("#middleware"));
		}
	});
	$(".dev-tool").tagit({
		afterTagAdded: function(event, ui) {
			reflectSkill($(this), $("#tool"));
		}
	});
	$(".dev-process").tagit({
		afterTagAdded: function(event, ui) {
			reflectSkill($(this), $("#process"));
		}
	});

	var reflectSkill = function(el,baseElement){
		setTimeout(() => {
			var addedValue = el.next("ul").children(".tagit-choice:last").children("span").text();
			var tags = [];
			tags = baseElement.val().split(",");
			if (tags[0] != '') {
				var bool = tags.every(function(e){
					return (e != addedValue);
				});
				if(bool){
					if(window.confirm('「' + addedValue + '」をスキル要約にも反映させますか？')){
						baseElement.tagit("createTag", addedValue);
					}
				}
			}else {
				if(window.confirm('「' + addedValue + '」をスキル要約にも反映させますか？')){
					baseElement.tagit("createTag", addedValue);
				}
			}
			baseElement.val(tags.join(","));

			setTimeout(() => {
				baseElement.next().find(".ui-autocomplete-input").autocomplete("close");
			}, 300);
		}, 300);
	}

});
