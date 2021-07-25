<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">
        <c:choose>
            <c:when test="${report !=null}">
                <h2>日報　詳細ページ</h2>

                <table>
                    <tbody>
                        <tr>
                            <th>氏名</th>
                            <td><c:out value="${report.employee.name}" /></td>
                        </tr>
                         <tr>
                            <th>日付</th>
                            <td><fmt:formatDate value="${report.report_date}" pattern="yyyy-MM-dd" /></td>
                        </tr>
                        <tr>
                            <th>内容</th>
                            <td>
                                <pre><c:out value="${report.content}" /></pre>
                            </td>
                        </tr>
                        <tr>
                            <th>登録日時</th>
                            <td>
                                <fmt:formatDate value="${report.created_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                        <tr>
                            <th>更新日時</th>
                            <td>
                                <fmt:formatDate value="${report.updated_at}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                        </tr>
                        <tr>
                            <th>承認状況</th>
                            <td>
                                <c:if test="${report.approval_flag == 0}">未承認</c:if>
                                <c:if test="${report.approval_flag == 1}">承認済み</c:if>
                            </td>
                        </tr>
                        <tr>
                            <th>コメント</th>
                            <td>
                                <pre><c:out value="${report.coment}" /></pre>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <br />
                <form method="POST" action="<c:url value='/like/create' />">
                    <input type="hidden" name="employee_id" value="${employee.id}">
                    <input type="hidden" name="report_id" value="${report.id}">
                    <button type="submit">いいね</button>
                </form>
                <br /><br />

                <h3>いいね履歴</h3>
                <table id="like_list">
                    <tbody>
                        <tr>
                             <tr>
                    <th class="like_name">氏名</th>
                    <th class="created_likee">日付</th>

                </tr>
                <c:forEach var="like" items="${likes}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td class="like_name"><c:out value="${like.employee.name}" /></td>
                        <td class="created_like"><fmt:formatDate value='${like.created_like}' pattern='yyyy-MM-dd' /></td>
                    </tr>
                </c:forEach>
                    </tbody>
                </table>

                <div id="pagination">
                （全 ${likes_count} 件）<br />
                <c:forEach var="i" begin="1" end="${((likes_count - 1) / 5) + 1}" step="1">
                    <c:choose>
                        <c:when test="${i == page}">
                            <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='/reports/show?id=${report.id}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                    </c:choose>
                </c:forEach>
                </div>


                <br />

                 <c:if test="${sessionScope.login_employee.admin_flag == 1}">
                    <p><a href="<c:url value="/reports/approval/edit?id=${report.id}" />">この日報を承認する</a>
                 </c:if>

                <c:if test="${sessionScope.login_employee.id == report.employee.id}">
                    <p><a href="<c:url value="/reports/edit?id=${report.id}" />">この日報を編集する</a></p>
                </c:if>
                </c:when>
                <c:otherwise>
                <h2>お探しのデータは見つかりませんでした。</h2>
                </c:otherwise>
                </c:choose>

        <p><a href="<c:url value="/reports/index" />">一覧に戻る</a></p>
    </c:param>
</c:import>