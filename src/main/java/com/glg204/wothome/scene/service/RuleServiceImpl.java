package com.glg204.wothome.scene.service;

import com.glg204.wothome.scene.dao.RuleDAO;
import com.glg204.wothome.scene.domain.Rule;
import com.glg204.wothome.scene.dto.RuleDTO;
import com.glg204.wothome.user.dao.UserDAO;
import com.glg204.wothome.user.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RuleServiceImpl implements RuleService {

    @Autowired
    UserDAO userDAO;

    @Autowired
    RuleDAO ruleDAO;

    @Autowired
    RuleDTOMapper ruleDTOMapper;


    @Override
    public boolean addRule(Principal principal, RuleDTO ruleDTO) {
        Optional<User> user = userDAO.getUserByEmail(principal.getName());
        Optional<Boolean> result = user.map(currentUser -> {
                    Rule r = ruleDTOMapper.fromDTO(ruleDTO, currentUser);
                    return ruleDAO.save(r) >= 0;
                }
        );
        return result.orElse(false);
    }

    @Override
    public List<RuleDTO> getRules(Principal principal) {
        Optional<User> user = userDAO.getUserByEmail(principal.getName());
        user.map(currentUser -> {
                    List<Rule> ruleList = ruleDAO.getRules(currentUser);
                    return new ArrayList<>();
                }
        );

        return new ArrayList<>();
    }
}
