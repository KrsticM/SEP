$(document).ready(function(){
	var formId = 'forma';
	$('#' + formId).submit(function (e) {
		handleFormConfirm(e, formId);
	}
	
	);
	
});


function handleFormConfirm(e, formId) {
	var form = $('#' + formId);
	e.preventDefault();
	
	var input = $('#' + formId + ' :input');
	var obj = {};
	
	for(var i = 0; i < input.length; i++){
		if(input[i].name){
			obj[input[i].name] = input[i].value;
		}
	}
	
	
	
	$.ajax({
		type: form.attr('method'),
		url: form.attr('action'),
		contentType: "application/json",
		data: JSON.stringify(obj),
		success: function(odgovor){
			window.location.href = odgovor;
		},
		error: function(odgovor){
			alert(odgovor.responseText);
		}
	});
}