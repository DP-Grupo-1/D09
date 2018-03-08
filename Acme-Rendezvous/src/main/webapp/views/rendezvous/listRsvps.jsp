<%--
 * list.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<security:authorize access="hasRole('USER')">

<display:table name="rendezvouses" id="row" class="displayTag"
	requestURI="${requestURI}" keepStatus="true" pagesize="5">

			<display:column>
				<a href="rendezvous/display.do?rendezvousId=${row.id}">
					<spring:message code="rendezvous.list.display" />
				</a>
			</display:column>

	<spring:message code="rendezvous.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />

	<spring:message code="rendezvous.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}"
		sortable="true" />

	<spring:message code="rendezvous.moment" var="momentHeader" />
	<spring:message code="rendezvous.moment.format" var="momentFormat" />
	<display:column property="moment" class="fecha" title="${momentHeader}"
		titleKey="rendezvous.moment" sortable="true"
		format="{0,date,${momentFormat }}" />

	<spring:message code="rendezvous.creator" var="creatorHeader" />
	<display:column title="${creatorHeader}" sortable="true">
		<a href="user/display.do?userId=<jstl:out value="${row.creator.id}"/>">
			<jstl:out value="${row.creator.name} ${row.creator.surname}" />
		</a>
	</display:column>


	<spring:message code="rendezvous.adultOnly" var="adultOnlyHeader" />
	<display:column property="adultOnly" title="${adultOnlyHeader}"
		sortable="true" />

	<spring:message code="rendezvous.flag" var="flagHeader" />
	<display:column property="flag" class="indicador" title="${flagHeader}" sortable="true" />
	
	
	<display:column>
				<a href="comment/list.do?rendezvousId=${row.id}">
					<spring:message code="rendezvous.list.comment" />
				</a>
			</display:column>

<display:column>
	<div>
		<a href="comment/user/create.do?rendezvousId=${row.id}"><spring:message
				code="comment.create" /></a>
	</div>
</display:column>
</display:table>

	<script>	
		window.onload = function prueba() {
			
			var actualFlag = document.getElementsByClassName("indicador");
			var rendezvousDate = document.getElementsByClassName("fecha");
			
			
			var d = new Date();
			
			var minutos = d.getMinutes();
			var hora = d.getHours();
			
			var dia = d.getDate();
			var mes = d.getMonth() +1;
			var ano = d.getFullYear().toString().substr(-2);
			
			var i;
			
			
			for (i = 0; i < actualFlag.length; i++) {
				
				var campos = rendezvousDate[i].textContent.split(' ');
				
				// 18/05/23
				//dates[0] = 18, dates[1] = 05, dates[2] = 23
				var dates = campos[0].split("/");
				
				// 13:30
				//horas[0] = 13, horas[1] = 30
				var horas = campos[1].split(":");
			if(actualFlag[i].textContent != "DELETED"){
				if(dates[0] > ano){
					actualFlag[i].innerHTML = "ACTIVE";
				} else if((dates[0] == ano) && dates[1] > mes){
					actualFlag[i].innerHTML = "ACTIVE";
				} else if((dates[0] == ano) && (dates[1] == mes) && dates[2] > dia){
					actualFlag[i].innerHTML = "ACTIVE";
				} else if((dates[0] == ano) && (dates[1] == mes) && (dates[2] == dia) && horas[0] > hora){
					actualFlag[i].innerHTML = "ACTIVE";
			} else if((dates[0] == ano) && (dates[1] == mes) && (dates[2] == dia) && (horas[0] == hora) && (horas[1] > minutos)){
					actualFlag[i].innerHTML = "ACTIVE";
				} else {
					actualFlag[i].innerHTML = "PASSED";
				}
			}
			
		}
		};
		</script>
	
</security:authorize>