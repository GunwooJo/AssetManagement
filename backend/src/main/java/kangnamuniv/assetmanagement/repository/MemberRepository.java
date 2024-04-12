package kangnamuniv.assetmanagement.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import kangnamuniv.assetmanagement.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
public class MemberRepository {

    private final EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member findByLoginId(String login_id) {
        return em.createQuery("select m from Member m where m.login_id = :login_id", Member.class)
                .setParameter("login_id", login_id)
                .getSingleResult();
    }

    public void saveConnectedId(String login_id, String connected_id) {

        try {
            Member foundMember = em.createQuery("select m from Member m where m.login_id = :login_id", Member.class)
                    .setParameter("login_id", login_id).getSingleResult();
            foundMember.setConnected_id(connected_id);

        } catch (NoResultException e) {
            e.printStackTrace();
        }

    }
}
