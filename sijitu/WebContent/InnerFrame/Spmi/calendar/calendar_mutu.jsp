<!DOCTYPE html>
<html>
<head>

<%@ page import="beans.tools.*" %>
<%@ page import="beans.setting.*" %>
<%@ page import="beans.dbase.spmi.*"%>
<%@ page import="java.util.Vector" %>
<%@ page import="java.util.ListIterator" %>
<%@ page import="java.util.StringTokenizer" %>
<link rel="stylesheet" type="text/css" href="<%=Constants.getRootWeb() %>/fullPageLayout/screen.css" media="screen" />
<link href='<%=Constants.getRootWeb() %>/x_full_calendar/fullcalendar.css' rel='stylesheet' />
<link href='<%=Constants.getRootWeb() %>/x_full_calendar/fullcalendar.print.css' rel='stylesheet' media='print' />
<script src='<%=Constants.getRootWeb() %>/x_full_calendar/jquery/jquery-1.10.2.js'></script>
<script src='<%=Constants.getRootWeb() %>/x_full_calendar/fullcalendar.js'></script>
<script src='<%=Constants.getRootWeb() %>/x_full_calendar/jquery/jquery-ui.custom.min.js'></script>




<%
beans.login.InitSessionUsr validUsr = (beans.login.InitSessionUsr)session.getAttribute("validUsr");
String at_menu_dash = request.getParameter("at_menu_dash");
String kdpst_nmpst_kmp = request.getParameter("kdpst_nmpst_kmp");
Vector v= (Vector)request.getAttribute("v");
request.removeAttribute("v");
kdpst_nmpst_kmp=kdpst_nmpst_kmp.replace("`", "-");
StringTokenizer st = new StringTokenizer(kdpst_nmpst_kmp,"-");
String kdpst = st.nextToken();
String nmpst = st.nextToken();
String kdkmp = st.nextToken();
//System.out.println("kdpst_nmpst_kmp=="+kdpst_nmpst_kmp);

String src_limit = "3";
String starting_no = "1";
Vector v_todo = ToolSpmi.getDataKalenderMutu();
%>

<script>

	$(document).ready(function() {
	    var date = new Date();
		var d = date.getDate();
		var m = date.getMonth();
		var y = date.getFullYear();
		
		/*  className colors
		
		className: default(transparent), important(red), chill(pink), success(green), info(blue)
		
		*/		
		
		  
		/* initialize the external events
		-----------------------------------------------------------------*/
	
		$('#external-events div.external-event').each(function() {
		
			// create an Event Object (http://arshaw.com/fullcalendar/docs/event_data/Event_Object/)
			// it doesn't need to have a start or end
			var eventObject = {
				title: $.trim($(this).text()) // use the element's text as the event title
			};
			
			// store the Event Object in the DOM element so we can get to it later
			$(this).data('eventObject', eventObject);
			
			// make the event draggable using jQuery UI
			$(this).draggable({
				zIndex: 999,
				revert: true,      // will cause the event to go back to its
				revertDuration: 0  //  original position after the drag
			});
			
		});
	
	
		/* initialize the calendar
		-----------------------------------------------------------------*/
		
		var calendar =  $('#calendar').fullCalendar({
			header: {
				left: 'title',
				center: 'agendaDay,agendaWeek,month',
				right: 'prev,next today'
			},
			editable: true,
			firstDay: 1, //  1(Monday) this can be changed to 0(Sunday) for the USA system
			selectable: true,
			defaultView: 'month',
			
			axisFormat: 'h:mm',
			columnFormat: {
                month: 'ddd',    // Mon
                week: 'ddd d', // Mon 7
                day: 'dddd M/d',  // Monday 9/7
                agendaDay: 'dddd d'
            },
            titleFormat: {
                month: 'MMMM yyyy', // September 2009
                week: "MMMM yyyy", // September 2009
                day: 'MMMM yyyy'                  // Tuesday, Sep 8, 2009
            },
			allDaySlot: false,
			selectHelper: true,
			/*
			select: function(start, end, allDay) {
				var title = prompt('Event Title:');
				if (title) {
					calendar.fullCalendar('renderEvent',
						{
							title: title,
							start: start,
							end: end,
							allDay: allDay
						},
						true // make the event "stick"
					);
				}
				calendar.fullCalendar('unselect');
			},
			droppable: true, // this allows things to be dropped onto the calendar !!!
			drop: function(date, allDay) { // this function is called when something is dropped
			
				// retrieve the dropped element's stored Event Object
				var originalEventObject = $(this).data('eventObject');
				
				// we need to copy it, so that multiple events don't have a reference to the same object
				var copiedEventObject = $.extend({}, originalEventObject);
				
				// assign it the date that was reported
				copiedEventObject.start = date;
				copiedEventObject.allDay = allDay;
				
				// render the event on the calendar
				// the last `true` argument determines if the event "sticks" (http://arshaw.com/fullcalendar/docs/event_rendering/renderEvent/)
				$('#calendar').fullCalendar('renderEvent', copiedEventObject, true);
				
				// is the "remove after drop" checkbox checked?
				if ($('#drop-remove').is(':checked')) {
					// if so, remove the element from the "Draggable Events" list
					$(this).remove();
				}
				
			},
			*/
			events: [
<%
if(v_todo!=null && v_todo.size()>0) {
	ListIterator li = v_todo.listIterator();
	while(li.hasNext()) {
		String brs = (String)li.next();
		//System.out.println("baris="+brs);
		st = new StringTokenizer(brs,"~");
		String tgl_plan = st.nextToken();
		String tgl_ril = st.nextToken();
		String tgl_done = st.nextToken();
		String nm_kegiatan = st.nextToken();
		String ket_kegiatan = st.nextToken();
		//while(ket_kegiatan.contains("kutip")) {
		//	ket_kegiatan = ket_kegiatan.replace("kutip","-");	
		//}
		//while(ket_kegiatan.contains("nuline")) {
		//	ket_kegiatan = ket_kegiatan.replace("nuline","\n");	
		//}
		String waktu_plan = st.nextToken();
		String waktu_ril = st.nextToken();
		String waktu_end = st.nextToken();
		String id_eval = st.nextToken();
		String id_kendal = st.nextToken();
		String tgl_kendal = st.nextToken();
		String tgl_next_eval = st.nextToken();
		st = new StringTokenizer(tgl_plan,"-");
		String thn = st.nextToken();
		String bln = st.nextToken();
		String tgl = st.nextToken();
		
		if(!Checker.isStringNullOrEmpty(tgl_ril)) {
			st = new StringTokenizer(tgl_ril,"-");
			thn = st.nextToken();
			bln = st.nextToken();
			tgl = st.nextToken();
		}
		String hr_plan = "09";
		String mn_plan = "00";
		String hr_ril = "09";
		String mn_ril = "00";
		String hr_end = "09";
		String mn_end = "00";
		if(!Checker.isStringNullOrEmpty(waktu_plan)) {
			st = new StringTokenizer(waktu_plan,":");
			hr_plan = st.nextToken();
			mn_plan = st.nextToken();
		}	
		if(!Checker.isStringNullOrEmpty(waktu_ril)) {
			st = new StringTokenizer(waktu_ril,":");
			hr_ril = st.nextToken();
			mn_ril = st.nextToken();
		}	
		
		if(!Checker.isStringNullOrEmpty(waktu_end)) {
			st = new StringTokenizer(waktu_end,":");
			hr_end = st.nextToken();
			mn_end = st.nextToken();
		}
		
		String hh_sta="09",mm_sta="00",hh_end="10",mm_end="00";
		if(!Checker.isStringNullOrEmpty(waktu_end)) {
			hh_end = hr_end;
			mm_end = mn_end;
		}
		
		if(!Checker.isStringNullOrEmpty(waktu_ril)) {
			hh_sta = hr_ril;
			mm_sta = mn_ril;
		}
		else if(!Checker.isStringNullOrEmpty(waktu_plan)) {
			hh_sta = hr_plan;
			mm_sta = mn_plan;
		}
		String info_tambahan="null";
		//if(!id_eval.equalsIgnoreCase("0") && id_kendal.equalsIgnoreCase("0") ) {
		if((Integer.parseInt(id_eval)>0) && (Integer.parseInt(id_kendal)<1)) {	
			info_tambahan = "Belum Ada Pengendalian Hasil Survey/Monitoring !!!";
		}
		//System.out.println("info_tambahan="+info_tambahan);
%>
				{
<%
		if(Checker.isStringNullOrEmpty(info_tambahan)) {
%>
					title: '<%=nm_kegiatan.toUpperCase()%>'+'\n<%=ket_kegiatan.toLowerCase()%>',
<%
		}
		else {
			%>
					title: '<%=nm_kegiatan.toUpperCase()%>'+'\n<%=ket_kegiatan.toLowerCase()%>\nNOTE:\n<%=info_tambahan%>',
<%			
		}	
%>
					start: new Date(<%=thn%>, <%=Integer.parseInt(bln)-1%>, <%=tgl%>, <%=hh_sta%>, <%=mm_sta%>),
					<%
		if(!Checker.isStringNullOrEmpty(tgl_done)) {
			st = new StringTokenizer(tgl_done,"-");
			thn = st.nextToken();
			bln = st.nextToken();
			tgl = st.nextToken();
			%>
					end: new Date(<%=thn%>, <%=Integer.parseInt(bln)-1%>, <%=tgl%>, <%=hh_end%>, <%=mm_end%>),
<%
		}
%>					
					allDay: false,
<%
		if((Integer.parseInt(id_eval)<0)) {
			%>
						className: 'success'				
<%	
		}
		else {
			try {
				java.sql.Date dt = java.sql.Date.valueOf(tgl_done);
	%>
						className: 'info'
	<%
			}
			catch(Exception e) {
	%>
						className: 'important'
	<%
			}	
		}
			
%>
					
					
<%
		if(li.hasNext()) {
%>
				},
<%
		}
		else {
%>
				}
<%
		}
	}
}		
/*
%>
				{
					title: 'All Day Event',
					start: new Date(y, m, 1)
				},
				{
					id: 999,
					title: 'Repeating Event',
					start: new Date(y, m, d-3, 16, 0),
					allDay: false,
					className: 'info'
				},
				{
					id: 999,
					title: 'Repeating Event',
					start: new Date(y, m, d+4, 16, 0),
					allDay: false,
					className: 'info'
				},
				{
					title: 'Meeting',
					start: new Date(y, m, d, 10, 30),
					allDay: false,
					className: 'important'
				},
				{
					title: 'Lunch',
					start: new Date(y, m, d, 12, 0),
					end: new Date(y, m, d, 14, 0),
					allDay: false,
					className: 'important'
				},
				{
					title: 'Birthday Party',
					start: new Date(y, m, d+1, 19, 0),
					end: new Date(y, m, d+1, 22, 30),
					allDay: false,
				},
				
				{
					title: 'Birthday Party4',
					start: new Date(y, m, 26, 19, 0),
					end: new Date(y, m, 26, 22, 30),
<%
String target = Constants.getRootWeb()+"/InnerFrame/Spmi/home_spmi.jsp";
String uri = request.getRequestURI();
String url = PathFinder.getPath(uri, target);
%>
				
					url: '<%=(url+"?kdpst_nmpst_kmp=74201-HUKUM-PST")%>',
					allDay: false,
				},
				{
					title: 'Click for Google',
					start: new Date(y, m, 28),
					end: new Date(y, m, 29),
					url: 'http://google.com/',
					className: 'success'
				}
<%
*/
%>
			],			
		});
		
		
	});

</script>
<style>

	body {
		margin-top: 0px;
		text-align: center;
		font-size: 14px;
		font-family: "Helvetica Nueue",Arial,Verdana,sans-serif;
		background-color: #DDDDDD;
		}
		
	#wrap {
		width: 1100px;
		margin: 0 auto;
		}
		
	#external-events {
		float: left;
		width: 150px;
		padding: 0 10px;
		text-align: left;
		}
		
	#external-events h4 {
		font-size: 16px;
		margin-top: 0;
		padding-top: 1em;
		}
		
	.external-event { /* try to mimick the look of a real event */
		margin: 10px 0;
		padding: 2px 4px;
		background: #3366CC;
		color: #fff;
		font-size: .85em;
		cursor: pointer;
		}
		
	#external-events p {
		margin: 1.5em 0;
		font-size: 11px;
		color: #666;
		}
		
	#external-events p input {
		margin: 0;
		vertical-align: middle;
		}

	#calendar {
/* 		float: right; */
        margin: 0 auto;
		width: 900px;
		background-color: #FFFFFF;
		  border-radius: 6px;
        box-shadow: 0 1px 2px #C3C3C3;
		}

</style>
<div id="header" style="background:white">
	<ul>

		<li><a href="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/InnerFrame/Spmi/home_spmi.jsp?kdpst_nmpst_kmp=<%=kdpst_nmpst_kmp %>" target="inner_iframe">BACK<span><b style="color:#eee">&nbsp</b></span></a></li>

	</ul>
</div>
</head>
<body>
&nbsp<br>
<center>
<form action="go.refreshCalenderMutu">
<p style="padding:10px 10px">
	<input type="image" title="Refresh" src="<%=Constants.getRootWeb()+"/" %>${PageContext.ServletContext.ContextPath}/Metro-UI-CSS-master/docs/images/refresh.png" height="50px" width="50px"/>
	<input type="hidden" name="kdpst_nmpst_kmp" value="<%=kdpst_nmpst_kmp %>"/>
</p>	 
</form>
</center>
<div id='wrap'>

<div id='calendar'></div>

<div style='clear:both'></div>
</div>
</body>
</html>
