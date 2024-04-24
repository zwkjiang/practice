package com.example.common;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class TeamMemberInvocation<T>  implements InvocationHandler {
    private T target;

    public TeamMemberInvocation(T target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        method.invoke(target,args);
        return null;
    }
}
