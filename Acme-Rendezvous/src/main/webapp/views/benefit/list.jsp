<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>


<display:table pagesize="10" class="displaytag" keepStatus="true"
	name="benefits" requestURI="${requestURI}" id="row">
	<security:authentication property="principal" var ="loggedactor"/>
	
	<spring:message code="benefit.name" var="nameHeader" />
	<display:column property="name" title="${nameHeader}" sortable="true" />
	
	<spring:message code="benefit.description" var="descriptionHeader" />
	<display:column property="description" title="${descriptionHeader}" sortable="true" />
	
	<spring:message code="benefit.picture" var="pictureHeader" />
	<display:column property="picture" title="${pictureHeader}" sortable="true" />
	
	<spring:message code="benefit.flag" var="flagHeader" />
	<display:column property="flag" title="${flagHeader}" sortable="true" />
	
	<security:authorize access="hasRole('USER')">
		<display:column>
			<jstl:if test="${row.flag != 'CANCELLED'}">
				<a href="request/user/requestService.do?benefitId=${row.id}"><spring:message code="request.create"/></a>	
			</jstl:if>
		</display:column>
		
	</security:authorize>
	
	
	<security:authorize access="hasRole('MANAGER')">
		<display:column>
			<jstl:forEach var="item" items="${principalBenefits}">
				<jstl:if test="${row.flag eq 'ACTIVE' and item == row}">
						<a href="benefit/manager/edit.do?benefitId=${row.id}"><spring:message code="benefit.edit"/></a>
				</jstl:if>
			</jstl:forEach>
		</display:column>
	</security:authorize>
	
	<security:authorize access="hasRole('ADMIN')">
		<display:column>
			<jstl:if test="${row.flag != 'CANCELLED'}">
				<a href="benefit/administrator/cancel.do?benefitId=${row.id}"><spring:message code="benefit.cancel"/></a>
			</jstl:if>
		</display:column>
	</security:authorize>
</display:table>
<br/>
<security:authorize access="hasRole('MANAGER')">
	<a href="benefit/manager/create.do"><spring:message code="benefit.create"/></a>
</security:authorize>