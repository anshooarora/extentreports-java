package com.aventstack.extentreports.model.context;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.aventstack.extentreports.model.NamedAttribute;
import com.aventstack.extentreports.model.Test;
import com.aventstack.extentreports.model.service.TestService;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class NamedAttributeContextManager<T extends NamedAttribute> {
    private Set<NamedAttributeContext<T>> set = Collections
            .newSetFromMap(new ConcurrentHashMap<NamedAttributeContext<T>, Boolean>());

    public void addContext(T attr, Test test) {
        Optional<NamedAttributeContext<T>> opt = set.stream()
                .filter(x -> x.getAttr().getName().equals(attr.getName()))
                .findAny();
        if (opt.isPresent()) {
            List<Test> list = opt.get().getTestList();
            if (!list.stream().anyMatch(t -> t.getId() == test.getId()))
                list.add(test);
        } else {
            set.add(new NamedAttributeContext<>(attr, test));
        }
    }

    public void addContext(T attr, List<Test> testList) {
        testList.forEach(x -> addContext(attr, x));
    }

    /**
     * Remove a test from the context. This will also removed the context if
     * there are no tests present after test removal
     * 
     * @param test
     *            {@link Test}
     */
    public void removeTest(Test test) {
        Iterator<NamedAttributeContext<T>> iter = set.iterator();
        while (iter.hasNext()) {
            NamedAttributeContext<T> context = iter.next();
            TestService.deleteTest(context.getTestList(), test);
            if (context.getTestList().isEmpty())
                iter.remove();
        }
    }

    public boolean hasItems() {
        return !set.isEmpty();
    }
    
    public void resetAll() {
        set.stream().forEach(x -> x.refresh());
    }
}
