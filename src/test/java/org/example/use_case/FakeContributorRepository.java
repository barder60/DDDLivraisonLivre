package org.example.use_case;

import org.example.model.*;

import java.util.List;
import java.util.stream.Collectors;

public class FakeContributorRepository implements ContributorRepository {
    private List<Contributor> contributors = List.of(
            new Contributor(
                    1L,
                    new User("firstcontri",
                        "lastcontri",
                        new UserEmailId("contri@butor.com"),
                        "contripassword"),
                    "organization",
                    Role.DRIVER
            ),
        new Contributor(
            2L,
            new User("firstanim",
                "firstanim",
                new UserEmailId("anim@mator.com"),
                "animpassword"),
            "organization",
            Role.ANIMATOR
        )
    );
    @Override
    public List<Contributor> findAllByIds(List<Long> ids) {
        return contributors.stream()
                .filter(contributor -> ids.contains(contributor.getId()))
                .collect(Collectors.toList());
    }

    public void removeContributorById(Long id) {
        contributors = contributors.stream()
            .filter(contributor -> !contributor.getId().equals(id))
            .collect(Collectors.toList());
    }
}
