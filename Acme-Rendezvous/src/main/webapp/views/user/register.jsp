<%--
 * register.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<form:form action="user/register.do" modelAttribute="register">



<%@ taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<acme:textbox code="user.username" path="username"/>
<acme:password code="user.password" path="password"/>
<acme:textbox code="user.name" path="name"/>
<acme:textbox code="user.surname" path="surname"/>

<spring:message code="user.optional" var="optional" />
<acme:textbox code="user.postalAddress" path="postalAddress" placeholder="${optional}" />
<acme:textbox code="user.phoneNumber" path="phoneNumber" placeholder="${optional}"/>
<acme:textbox code="user.email" path="email"/>

<spring:message code="user.adult" /> <form:checkbox path="adult"/>


<br>


<form:label path="accept" >
        <spring:message code="user.terms" />
    </form:label>
    <form:checkbox path="accept" id="terms" onchange="javascript: toggleSubmit()"/>
    <form:errors path="accept" cssClass="error" />

    <br/>

   <button type="submit" name="save" class="btn btn-primary" id="save" disabled>
        <spring:message code="user.save" />
    </button>

    <acme:cancel url="index.do" code="user.cancel"/>

    <script type="text/javascript">
        function toggleSubmit() {
            var accepted = document.getElementById("terms");
            if(accepted.checked){
                document.getElementById("save").disabled = false;
            } else{
                document.getElementById("save").disabled = true;
            }
        }
    </script>

</form:form>
