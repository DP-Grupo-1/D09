<%--
 * display.jsp
 *
 * Copyright (C) 2015 Universidad de Sevilla
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
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">



<!-- 									Rendezvous											-->
<jstl:if test="${ ((rendezvous.adultOnly == false) and (adult == false)) or ((rendezvous.adultOnly == true) && (adult == true)) 
	or ((rendezvous.adultOnly == false) and (adult == true)) or ((rendezvous.adultOnly == false) and (adult == null))}">


<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="rendezvous" requestURI="${requestURI}" id="row">

	<spring:message code="rendezvous.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true">
	</display:column>

	<spring:message code="rendezvous.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="true">
	</display:column>

    <spring:message code="rendezvous.moment" var="momentHeader" />
    <spring:message code="rendezvous.moment.format" var="rendezvousMomentFormat" />
	<display:column property="moment" title="${momentHeader}" class="fecha" sortable="true" format="{0,date,${rendezvousMomentFormat}}">
	</display:column>

	<spring:message code="rendezvous.picture" var="pictureHeader" />
	<display:column property="picture" title="${pictureHeader}" sortable="true">
	</display:column>

	<spring:message code="rendezvous.locationLatitude" var="locationLatitudeHeader" />
	<display:column property="locationLatitude" title="${locationLatitudeHeader}"
		sortable="true">
	</display:column>

	<spring:message code="rendezvous.locationLongitude" var="locationLongitudeHeader" />
	<display:column property="locationLongitude" title="${locationLongitudeHeader}"
		sortable="true">
	</display:column>
	
	<spring:message code="rendezvous.finalMode" var="finalModeHeader" />
	<display:column property="finalMode" title="${finalModeHeader}" sortable="true">
	</display:column>
	
	<spring:message code="rendezvous.adultOnly" var="adultOnlyHeader" />
		<display:column property="adultOnly" title="${adultOnlyHeader}" sortable="true" />
	
	<spring:message code="rendezvous.flag" var="flagHeader" />
	<display:column property="flag" class="indicador" title="${flagHeader}" sortable="true">
	</display:column>
	
	<spring:message code="rendezvous.creator" var="creatorHeader" />
    <display:column title="${creatorHeader}" sortable="true">
    <a href="user/display.do?userId=<jstl:out value="${row.creator.id}"/>">
    <jstl:out value="${row.creator.name} ${row.creator.surname}"/></a>
    </display:column> 
    
    <display:column >
	<a  href="user/list.do?rendezvousId=${row.id}"><spring:message code="rendezvous.attendants" /></a>
    </display:column>
    
     <display:column >
	<a href="announcement/list.do?rendezvousId=${row.id}"><spring:message code="rendezvous.announcements" /></a>
    </display:column>
    
    <spring:message code="rendezvous.comments" var="commentsHeader" />
    <display:column>
  		<a href="comment/list.do?rendezvousId=${row.id}">
 			<spring:message code="rendezvous.comments" />
 		</a>	
    </display:column> 
    
    <display:column>
			<a href="question/list.do?rendezvousId=${row.id}"><spring:message code="question.list"/></a>
	</display:column>
    <security:authorize access="hasRole('USER')">
    	<jstl:if test="${row.creator.userAccount.username != pageContext.request.userPrincipal.name}">
	    	 <jstl:if test="${noQuestions eq false && rsvped eq false && flag=='ACTIVE'}">
	    	 	<display:column>
					<a href="question/user/answerQuestions.do?rendezvousId=${row.id}"><spring:message code="rsvp.create"/></a>
				</display:column>
	    	 </jstl:if>
	    	 <jstl:if test="${noQuestions eq true && rsvped eq false && flag=='ACTIVE'}">
	    	 	<display:column>
					<a href="rendezvous/user/attend.do?rendezvousId=${row.id}">
		  			<spring:message code="rsvp.create" />
					</a>
			</display:column>
	    	 </jstl:if>
	    	 <jstl:if test="${rsvped==true}">
	    	 	<display:column>
	    	 		<a href="rendezvous/user/noAttend.do?rendezvousId=${row.id}">
		  			<spring:message code="rendezvous.noAttend" />
					</a>
	    	 	</display:column>
	    	 </jstl:if>
    	</jstl:if>
    	<jstl:if test="${row.creator.userAccount.username == pageContext.request.userPrincipal.name}">
    		<jstl:if test="${row.flag != 'DELETED'}">
	    		<jstl:if test="${row.finalMode == false}">
	    			 <a href="rendezvous/user/edit.do?rendezvousId=${row.id}">
           				<spring:message code="rendezvous.edit" />
        			 </a>
	    		</jstl:if>
	    		<display:column >
					<a  href="rendezvous/user/rendezvouses.do?rendezvousId=${row.id}"><spring:message code="rendezvous.link" /></a>
				</display:column>
				<display:column>
					<a href="question/user/create.do?rendezvousId=${row.id}"><spring:message code="question.create"/></a>
				</display:column>
	    	</jstl:if>
    	</jstl:if>
    </security:authorize>
    
    <security:authorize access="hasRole('ADMIN')">
    	<jstl:if test="${row.flag != 'DELETED'}">
    		 <a href="rendezvous/administrator/delete.do?rendezvousId=${row.id}">
			   <spring:message code="rendezvous.delete" />
			 </a>
    	</jstl:if>
    </security:authorize>
    
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
				
			if(actualFlag[i].textContent != "DELETED"){
				var campos = rendezvousDate[i].textContent.split(' ');
				
				// 18/05/23
				//dates[0] = 18, dates[1] = 05, dates[2] = 23
				var dates = campos[0].split("/");
				
				// 13:30
				//horas[0] = 13, horas[1] = 30
				var horas = campos[1].split(":");
				
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
		
<!-- 							 Rendezvouses linked										-->
<h1><spring:message code="rendezvous.linked" /></h1>
<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="rendezvous.rendezvouses" requestURI="${requestURI}" id="row">
	
	<!-- Display -->
		
	<display:column>
		<a href="rendezvous/display.do?rendezvousId=${row.id}">
			<spring:message code="rendezvous.list.display" />
		</a>
	</display:column>

	<spring:message code="rendezvous.name" var="rendezvousNameHeader" />
	<display:column property="name" title="${rendezvousNameHeader}" sortable="true">
	</display:column>

	<spring:message code="rendezvous.description" var="rendezvousDescriptionHeader" />
	<display:column property="description" title="${rendezvousDescriptionHeader}" 
	    sortable="true">
	</display:column>
	
	<spring:message code="rendezvous.moment" var="rendezvousMomentHeader" />
    <spring:message code="rendezvous.moment.format" var="rendezvousMomentFormat" />
	<display:column property="moment" class="fecha" title="${rendezvousMomentHeader}" 
	    titleKey="rendezvous.moment"
		sortable="true" format="{0,date,${rendezvousMomentFormat }}" />
		
	<spring:message code="rendezvous.adultOnly" var="rendezvousAdultOnlyHeader" />
	<display:column property="adultOnly" title="${rendezvousAdultOnlyHeader}" sortable="true" />
			
	<spring:message code="rendezvous.flag" var="rendezvousFlagHeader" />
	<display:column property="flag" class="indicador" title="${rendezvousFlagHeader}" sortable="true" />
	
	<spring:message code="rendezvous.creator" var="rendezvousCreatorHeader" />
    <display:column title="${rendezvousCreatorHeader}" sortable="true">
    <a href="profile/user/display.do?userId=<jstl:out value="${row.creator.id}"/>">
    <jstl:out value="${row.creator.name} ${row.creator.surname}"/></a>
    </display:column> 
   	    
   	<display:column>
   	<a  href="user/list.do?rendezvousId=${row.id}"><spring:message code="rendezvous.attendants" /></a>
    </display:column>
    
      <display:column>
	<a href="announcement/list.do?rendezvousId=${row.id}"><spring:message code="rendezvous.announcements" /></a>
    </display:column>


	
	<security:authorize access="hasRole('USER')">
	<jstl:if test="${rendezvous.creator.userAccount.username eq pageContext.request.userPrincipal.name}">
    <display:column>
	     <a href="rendezvous/user/removeLink.do?rendezvousId=${rendezvous.id}&rendezvousLinkedId=${row.id}">
	       <spring:message code="rendezvous.removeLink" />
	     </a>	
    </display:column>
    </jstl:if>
    </security:authorize> 
  
</display:table>		
</jstl:if>  
 
 <jstl:if test="${((rendezvous.adultOnly == true) && (adult == false)) or ((rendezvous.adultOnly == true) && (adult == null))}">
 <spring:message code="rendezvous.nothing" />
  </jstl:if>  