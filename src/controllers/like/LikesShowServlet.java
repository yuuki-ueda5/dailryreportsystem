package controllers.like;

import java.io.IOException;
import java.util.List;

import javax.persistence.EntityManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.Like;
import utils.DBUtil;

/**
 * Servlet implementation class LikesShowServlet
 */
@WebServlet("/like/show")
public class LikesShowServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public LikesShowServlet() {
        super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        EntityManager em = DBUtil.createEntityManager();

        int page;
        try{
            page = Integer.parseInt(request.getParameter("page"));
        }catch(Exception e){
            page = 1;
        }
        List<Like>likes = em.createNamedQuery("getAllLikes",Like.class)
                .setFirstResult(5 * (page - 1))
                .setMaxResults(5)
                .getResultList();

        long likes_count = (long)em.createNamedQuery("getLikesCount",Long.class)
                .getSingleResult();

        em.close();

        request.setAttribute("likes", likes);
        request.setAttribute("likes_count", likes_count);
        request.setAttribute("page", page);

        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/reports/show.jsp");
        rd.forward(request, response);
    }

}
