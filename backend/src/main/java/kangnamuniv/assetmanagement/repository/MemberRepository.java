package kangnamuniv.assetmanagement.repository;

import jakarta.persistence.EntityManager;
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

    public List<Member> findByLoginId(String login_id) {
        return em.createQuery("select m from Member m where m.login_id = :login_id", Member.class)
                .setParameter("login_id", login_id)
                .getResultList();
    }
}
