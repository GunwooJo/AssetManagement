package kangnamuniv.assetmanagement.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceException;
import kangnamuniv.assetmanagement.entity.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
@RequiredArgsConstructor
@Slf4j
public class MemberRepository {

    private final EntityManager em;

    public Long save(Member member) {
        em.persist(member);
        return member.getId();
    }

    public Member findByLoginId(String login_id) {
        try {
            return em.createQuery("select m from Member m where m.login_id = :login_id", Member.class)
                    .setParameter("login_id", login_id)
                    .getSingleResult();
        } catch (NoResultException e ) {
            log.debug("login_id를 가진 Member를 찾지 못함: " + e);
            return null;
        }

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

    public List<Member> findAllMember() {

        List<Member> foundMembers = new ArrayList<>();

        try {
            foundMembers = em.createQuery("select m from Member m where connected_id is not null", Member.class)
                    .getResultList();

        } catch (PersistenceException e) {
            log.error("connectedId를 가진 모든 멤버 불러오기 실패: ", e);

        } catch (Exception e) {
            log.error("connectedId를 가진 모든 멤버 불러오기 실패: " + e);
        }

        return foundMembers;
    }
}
