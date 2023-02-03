package it.jobhunt.JobHunt.repository.specification;

import it.jobhunt.JobHunt.entity.Job;
import it.jobhunt.JobHunt.helper.job.JobFilter;
import it.jobhunt.JobHunt.util.GeneralUtil;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.convert.QueryByExamplePredicateBuilder;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class JobSpecification {

    public static Specification<Job> get(Example<Job> example, JobFilter jobFilter) {
        return (root, query, builder) -> {
            final List<Predicate> predicates = new ArrayList<>();
            if (jobFilter != null) {
                if (jobFilter.getSalary() != null) {
                    if (jobFilter.getSalary().getSalary() != null) {
                        predicates.add(builder.greaterThanOrEqualTo(root.get("salary.salary"), jobFilter.getSalary().getSalary()));
                    }
                    if (jobFilter.getSalary().getSalary() != null) {
                        predicates.add(builder.lessThanOrEqualTo(root.get("salary.salary"), jobFilter.getSalary().getSalary()));
                    }

                    if (jobFilter.getSalary().getMinWage() != null) {
                        predicates.add(builder.lessThanOrEqualTo(root.get("salary.minWage"), jobFilter.getSalary().getMinWage()));
                    }
                    if (jobFilter.getSalary().getMaxWage() != null) {
                        predicates.add(builder.greaterThanOrEqualTo(root.get("salary.maxWage"), jobFilter.getSalary().getMaxWage()));
                    }
                }

                if (GeneralUtil.isNullOrEmpty(jobFilter.getRole())) {
                    predicates.add(root.get("role").in(jobFilter.getRole()));
                }

                if (GeneralUtil.isNullOrEmpty(jobFilter.getType())) {
                    predicates.add(root.get("type").in(jobFilter.getType()));
                }

                if (GeneralUtil.isNullOrEmpty(jobFilter.getLanguages())) {
                    predicates.add(root.get("languages").in(jobFilter.getLanguages()));
                }

                if (GeneralUtil.isNullOrEmpty(jobFilter.getIdCompanies())) {
                    predicates.add(root.get("company.id").in(jobFilter.getIdCompanies()));
                }
            }
            predicates.add(QueryByExamplePredicateBuilder.getPredicate(root, builder, example));
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }
}