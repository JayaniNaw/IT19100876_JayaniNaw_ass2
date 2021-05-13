$(document).ready(function() {

	if ($("#alertSuccess").text().trim() == "") {

		$("#alertSuccess").hide();
	}

	$("#alertError").hide();
});
$(document).on("click", "#btnSave", function(event) {
	// Clear alerts---------------------
	$("#alertSuccess").text("");
	$("#alertSuccess").hide();
	$("#alertError").text("");
	$("#alertError").hide();
	// Form validation-------------------
	var status = validateItemForm();
	if (status != true) {
		$("#alertError").text(status);
		$("#alertError").show();
		return;
	}
	// If valid------------------------
	var type = ($("#hidItemIDSave").val() == "") ? "POST" : "PUT";
	$.ajax(
		{
			url: "ProductsAPI",
			type: type,
			data: $("#formItem").serialize(),
			dataType: "text",
			complete: function(response, status) {
				onItemSaveComplete(response.responseText, status);
			}
		});
});
function onItemSaveComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully saved.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while saving.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while saving..");
		$("#alertError").show();
	}
	$("#hidItemIDSave").val("");
	$("#formItem")[0].reset();
}
$(document).on("click", ".btnUpdate", function(event) {
	$("#hidItemIDSave").val($(this).closest("tr").find('#hidItemIDUpdate').val());
	$("#productCode").val($(this).closest("tr").find('td:eq(0)').text());
	$("#productName").val($(this).closest("tr").find('td:eq(1)').text());
	$("#version").val($(this).closest("tr").find('td:eq(2)').text());
	$("#description").val($(this).closest("tr").find('td:eq(3)').text());
	$("#amount").val($(this).closest("tr").find('td:eq(4)').text());
	$("#nic").val($(this).closest("tr").find('td:eq(5)').text());
});
$(document).on("click", ".btnRemove", function(event) {
	$.ajax(
		{
			url: "ProductsAPI",
			type: "DELETE",
			data: "productId=" + $(this).data("itemid"),
			dataType: "text",
			complete: function(response, status) {
				onItemDeleteComplete(response.responseText, status);
			}
		});
});
function onItemDeleteComplete(response, status) {
	if (status == "success") {
		var resultSet = JSON.parse(response);
		if (resultSet.status.trim() == "success") {
			$("#alertSuccess").text("Successfully deleted.");
			$("#alertSuccess").show();
			$("#divItemsGrid").html(resultSet.data);
		} else if (resultSet.status.trim() == "error") {
			$("#alertError").text(resultSet.data);
			$("#alertError").show();
		}
	} else if (status == "error") {
		$("#alertError").text("Error while deleting.");
		$("#alertError").show();
	} else {
		$("#alertError").text("Unknown error while deleting..");
		$("#alertError").show();
	}
}
function validateItemForm() {
	// CODE
	if ($("#productCode").val().trim() == "") {
		return "Insert product code.format: p01";
	}
	var checkcode = $("#productCode").val().trim();
	var code = /^p(0?[1-9])(0?[1-9])$/;
	if (!code.test(checkcode)) {
		return "Insert Product code correctly.format: p01";
	}
	// NAME
	if ($("#productName").val().trim() == "") {
		return "Insert product name.";
	}
	//version
	if ($("#version").val().trim() == "") {
		return "Insert Product version.";
	}
	var vers = $("#version").val().trim();
	var vers2 = /^\d{4}[\/\-](0?[1-9])$/;
	if (!vers2.test(vers)) {
		return "Insert Product version.format: 2021-01";
	}
	// DESCRIPTION------------------------
	if ($("#description").val().trim() == "") {
		return "Insert Item Description.";
	}
	// PRICE-------------------------------
	if ($("#amount").val().trim() == "") {
		return "Insert product Price.";
	}
	// is numerical value
	var tmpPrice = $("#amount").val().trim();
	if (!$.isNumeric(tmpPrice)) {
		return "Insert a numerical value for Item Price.";
	}
	// convert to decimal price
	$("#amount").val(parseFloat(tmpPrice).toFixed(2));
	//NIC
	if ($("#nic").val().trim() == "") {
		return "Insert user nic. format: 123456789V";
	}
	var nic = $("#nic").val().trim();
	if(nic.length != 10 ){
		return "Please check NIC again";
	}
	var lastLetter = nic[nic.length-1];
	if(lastLetter != 'V'){
		return "Please check the lastletter. Ex:123456789V";
	}
	return true;
}
