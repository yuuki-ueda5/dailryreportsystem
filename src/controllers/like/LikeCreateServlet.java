package controllers.like;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Employee;
import models.Like;
import models.Report;
import models.validators.LikeValidator;
import utils.DBUtil;

/**
 * Servlet implementation class LikeCreateServlet
 */
@WebServlet("/like/create")
public class LikeCreateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LikeCreateServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        Like s = new Like();

        s.setEmployee((Employee)request.getSession().getAttribute("login_employee"));

        int reportId = Integer.parseInt(request.getParameter("report_id"));
        Report r = em.find(Report.class, reportId);
        s.setReport(r);

        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        s.setCreated_like(currentTime);

        List<String>errors = LikeValidator.validate(s);
        if(errors.size() > 0){
            em.close();

            request.setAttribute("_token", request.getSession().getId());
            request.setAttribute("like", s);
            request.setAttribute("errors", errors);

            RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
            rd.forward(request, response);
        }else{
            em.getTransaction().begin();
            em.persist(s);
            em.getTransaction().commit();
            em.close();
            request.getSession().setAttribute("flush", "いいねしました。");

            response.sendRedirect(request.getContextPath() + "/reports/show?id=" + r.getId());
        }
        }
    }


