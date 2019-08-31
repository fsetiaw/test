<!DOCTYPE html>
<html>
<head>
  <title>Bootstrap-select test page</title>
	<%@ page import="beans.setting.*" %>
	<%@ page import="beans.tools.*" %>
	<%@ page import="java.util.Vector" %>
	<%@ page import="java.util.ListIterator" %>


	<title>UNIVERSITAS SATYAGAMA</title>
	<meta http-equiv="Content-Type" content="application/xhtml+xml; charset=utf-8" />
	<meta name="description" content="" />
	<meta name="keywords" content="" />

  <meta charset="utf-8">

  <link rel="stylesheet" href="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/css/bootstrap.min.css">
  <link rel="stylesheet" href="<%=Constants.getRootWeb() %>/bootstrap-select-1.12.2/dist/css/bootstrap-select.css">
  <script src="<%=Constants.getRootWeb() %>/bootstrap-select-1.12.2/js/jquery.min.js"></script>
  <script src="<%=Constants.getRootWeb() %>/bootstrap-3.3.5-dist/js/bootstrap.min.js"></script>
  <script src="<%=Constants.getRootWeb() %>/bootstrap-select-1.12.2/dist/js/bootstrap-select.js"></script>
  <script>
  $(document).ready(function () {
    var mySelect = $('#first-disabled2');

    $('#special').on('click', function () {
      mySelect.find('option:selected').prop('disabled', true);
      mySelect.selectpicker('refresh');
    });

    $('#special2').on('click', function () {
      mySelect.find('option:disabled').prop('disabled', false);
      mySelect.selectpicker('refresh');
    });

    $('#basic2').selectpicker({
      liveSearch: true,
      maxOptions: 1
    });
  });
</script>
  
</head>
<body>





        <select id="basic" class="selectpicker show-tick form-control" data-live-search="true">
          <option>cow</option>

            <option>ASD</option>
            <option selected>Bla</option>
            <option>Ble</option>
          </optgroup>
        </select>





</body>
</html>
