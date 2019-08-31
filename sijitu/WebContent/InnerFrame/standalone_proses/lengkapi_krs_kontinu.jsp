<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script>
function checkProgress() {
    $.getJSON('progressServlet?processId=someid', function(progress) {
        $('#progress').text(progress + "%");
        $('#progress .bar').width(progress);

        if (parseInt(progress) < 100) {
            setTimeout(checkProgress, 1000); // Checks again after one second.
        }
    });
}

</script>
</head>
<body>
sampe
<div>
</div>
</body>
</html>