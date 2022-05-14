package univ.tuit.applyjobbot.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import univ.tuit.applyjobbot.domain.Requirement;
import univ.tuit.applyjobbot.repo.RequirementRepository;

import java.util.List;

@Component
@Repository
public class RequirementCache implements Cache<Requirement> {

    @Autowired
    RequirementRepository requirementRepository;

    @Override
    public Requirement add(Requirement requirement) {

        return requirementRepository.save(requirement);
    }

    @Override
    public void remove(Requirement requirement) {

    }

    @Override
    public Requirement update(Requirement requirement) {
        return null;
    }

    @Override
    public Requirement findBy(Long id, Integer sequence) {
        return null;
    }

    @Override
    public List<Requirement> getAll() {
        return null;
    }

    @Override
    public List<Requirement> findByJobId(String s) {
        return requirementRepository.findByJobId(s);
    }
}
