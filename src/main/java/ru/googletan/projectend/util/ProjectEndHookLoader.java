package ru.googletan.projectend.util;

import gloomyfolken.hooklib.fields.ASJASM;
import gloomyfolken.hooklib.minecraft.HookLoader;
import gloomyfolken.hooklib.minecraft.PrimaryClassTransformer;

public class ProjectEndHookLoader extends HookLoader
{
    // включает саму HookLib'у. Делать это можно только в одном из HookLoader'ов. При желании, можно включить gloomyfolken.hooklib.minecraft.HookLibPlugin и не указывать здесь это вовсе.
    @Override
    public String[] getASMTransformerClass() {
        return new String[]{PrimaryClassTransformer.class.getName()};
    }

    @Override
    public void registerHooks()
    {
       registerHookContainer("ru.googletan.projectend.util.MyHooks");
    }
}

