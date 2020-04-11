package ru.googletan.projectend.Hooks;

import gloomyfolken.hooklib.fields.ASJASM;
import gloomyfolken.hooklib.minecraft.HookLoader;
import gloomyfolken.hooklib.minecraft.PrimaryClassTransformer;

public class ExampleHookLoader extends HookLoader
{
    // включает саму HookLib'у. Делать это можно только в одном из HookLoader'ов. При желании, можно включить gloomyfolken.hooklib.minecraft.HookLibPlugin и не указывать здесь это вовсе.
    @Override
    public String[] getASMTransformerClass() {
        return new String[]{PrimaryClassTransformer.class.getName()};
    }

    @Override
    public void registerHooks()
    {
        registerHookContainer("ru.googletan.projectend.Hooks.MyHooks");
        ASJASM.registerFieldHookContainer("ru.googletan.projectend.Hooks.FieldHooks");
    }
}

