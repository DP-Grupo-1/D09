<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

<!-- Listing grid -->
<div class=center-text>
<display:table name="rendezvouses" id="row" requestURI="rendezvous/user/rendezvouses.do" pagesize="5" class="displaytag" >

<!-- Attributes -->

  <spring:message code="rendezvous.name" var="nameHeader" />
  <display:column property="name" title="${nameHeader}" sortable="true" />
			
  <spring:message code="rendezvous.description" var="descriptionHeader" />
  <display:column property="description" title="${descriptionHeader}" sortable="true" />		

  <spring:message code="rendezvous.moment" var="momentHeader" />
  <spring:message code="rendezvous.moment.format" var="momentFormat" />
  <display:column property="moment" title="${momentHeader}" titleKey="rendezvous.moment"
	sortable="true" format="{0,date,${momentFormat }}" />
   	    
  <spring:message code="rendezvous.finalMode" var="finalModeHeader" />
  <display:column property="finalMode" title="${finalModeHeader}" sortable="true" />
        	
  <spring:message code="rendezvous.adultOnly" var="adultOnlyHeader" />
  <display:column property="adultOnly" title="${adultOnlyHeader}" sortable="true" />
			
  <spring:message code="rendezvous.flag" var="flagHeader" />
  <display:column property="flag" title="${flagHeader}" sortable="true" />
		

<!-- Action links -->
   	<security:authorize access="hasRole('USER')">
   	<jstl:set var="linked" value="false" />
		<jstl:forEach var="item" items="${rendezvous.rendezvouses}">
				
		  <jstl:if test="${item.id eq row.id}">
		    <jstl:set var="linked" value="true" />
		  </jstl:if>
		</jstl:forEach>
	<display:column>
	
	<jstl:choose> 
   	<jstl:when test="${!linked}">
	    <a href="rendezvous/user/link.do?rendezvousId=${rendezvousId}&rendezvousLinkId=${row.id}">
	        <spring:message code="rendezvous.link" />
	    </a>	
	</jstl:when>
	<jstl:otherwise>
	   <a href="rendezvous/user/removeLink.do?rendezvousId=${rendezvousId}&rendezvousLinkedId=${row.id}">
	       <spring:message code="rendezvous.removeLink" />
	     </a>	
	 </jstl:otherwise>
	</jstl:choose>
   	</display:column>
   	
  	</security:authorize>   

</display:table>
</div> 