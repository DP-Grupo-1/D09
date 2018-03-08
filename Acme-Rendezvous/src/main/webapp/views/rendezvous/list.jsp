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



<!-- Listing grid -->

	<display:table pagesize="5" class="displaytag" keepStatus="true"
		name="rendezvouses" requestURI="${requestURI}" id="row">
		
	<security:authentication property="principal" var ="loggedactor"/>
		<!-- Display -->
		
			<display:column>
				<a href="rendezvous/display.do?rendezvousId=${row.id}">
					<spring:message code="rendezvous.list.display" />
				</a> <br/>
				
				<jstl:if test="${row.flag != 'DELETED'}">
				  <jstl:if test="${row.creator.userAccount.username eq pageContext.request.userPrincipal.name}">
		  			<a href="announcement/user/create.do?rendezvousId=${row.id}"><spring:message code="announcement.create" /></a>
   		 		</jstl:if>
					<br/>
				<jstl:if test="${row.creator.userAccount.username eq pageContext.request.userPrincipal.name}">
				<jstl:if test="${row.finalMode == false}">
					<a href="rendezvous/user/edit.do?rendezvousId=${row.id}"><spring:message code="rendezvous.edit"/></a>
					</jstl:if>
				</jstl:if>
			</jstl:if>
			
			<security:authorize access="hasRole('ADMIN')">
			<jstl:if test="${row.flag != 'DELETED'}">
				
				  
						<a href="rendezvous/administrator/delete.do?rendezvousId=${row.id}"><spring:message code="rendezvous.delete"/></a>
					
				
			</jstl:if>
   			 </security:authorize>
    
			</display:column>
		
<!-- Attributes -->
		
		<spring:message code="rendezvous.name" var="nameHeader" />
		<display:column property="name" title="${nameHeader}"
			sortable="true" />
			

   	    
   	    <spring:message code="rendezvous.creator" var="creatorHeader" />
        <display:column title="${creatorHeader}" sortable="true">
        <a href="user/display.do?userId=<jstl:out value="${row.creator.id}"/>">
        <jstl:out value="${row.creator.name} ${row.creator.surname}"/></a>
        </display:column> 
   	    
   	    <spring:message code="rendezvous.finalMode" var="finalModeHeader" />
		<display:column property="finalMode" title="${finalModeHeader}" sortable="true" />
        	
		<spring:message code="rendezvous.adultOnly" var="adultOnlyHeader" />
		<display:column property="adultOnly" title="${adultOnlyHeader}" sortable="true" />
			
			

		<spring:message code="rendezvous.flag" var="flagHeader" />
		<display:column property="flag" class="indicador" title="${flagHeader}" sortable="true" />
		

		
		 <display:column>
			<a href="user/list.do?rendezvousId=${row.id}"><spring:message code="rendezvous.attendants" /></a>
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
		
		
		
	<!-- Action links -->

    <security:authorize access="hasRole('USER')">
	   <div>
		  <a href="rendezvous/user/create.do"><spring:message code="rendezvous.create" /></a>
	   </div>
    </security:authorize>
    
    
