package com.epam.spring.homework6.util;

import com.epam.spring.homework6.controller.dto.CategoryDto;
import com.epam.spring.homework6.controller.dto.TranslationDto;
import com.epam.spring.homework6.service.model.Category;
import com.epam.spring.homework6.service.model.Translation;
import com.epam.spring.homework6.service.model.enums.CategoryStatus;

import java.util.ArrayList;
import java.util.List;

public class CategoryTestData {

    public static final Integer ID = 1;
    public static final Integer OTHER_ID = 2;
    public static final String NAME_EN = "category";
    public static final String NAME_UA = "категорія";

    public static List<CategoryDto> createCategoryDtoList() {
        List<CategoryDto> categoryDtoList = new ArrayList<>();
        List<TranslationDto> translationDtoListForFirstCategoryDto = new ArrayList<>();
        List<TranslationDto> translationDtoListForSecondCategoryDto = new ArrayList<>();
        TranslationDto firstTranslationDto = new TranslationDto();
        firstTranslationDto.setLang("en");
        firstTranslationDto.setName("category1");
        TranslationDto secondTranslationDto = new TranslationDto();
        secondTranslationDto.setLang("ua");
        secondTranslationDto.setName("категорія1");
        TranslationDto thirdTranslationDto = new TranslationDto();
        thirdTranslationDto.setLang("en");
        thirdTranslationDto.setName("category2");
        TranslationDto forthTranslationDto = new TranslationDto();
        forthTranslationDto.setLang("ua");
        forthTranslationDto.setName("категорія2");
        translationDtoListForFirstCategoryDto.add(firstTranslationDto);
        translationDtoListForFirstCategoryDto.add(secondTranslationDto);
        translationDtoListForSecondCategoryDto.add(thirdTranslationDto);
        translationDtoListForSecondCategoryDto.add(forthTranslationDto);
        CategoryDto firstCategoryDto = new CategoryDto();
        firstCategoryDto.setId(1);
        firstCategoryDto.setStatus(CategoryStatus.OPENED.name());
        firstCategoryDto.setTranslations(translationDtoListForFirstCategoryDto);
        CategoryDto secondCategoryDto = new CategoryDto();
        secondCategoryDto.setId(2);
        secondCategoryDto.setStatus(CategoryStatus.OPENED.name());
        secondCategoryDto.setTranslations(translationDtoListForSecondCategoryDto);
        categoryDtoList.add(firstCategoryDto);
        categoryDtoList.add(secondCategoryDto);
        return categoryDtoList;
    }

    public static List<Category> createCategoryList() {
        List<Category> categories = new ArrayList<>();
        List<Translation> translationsForFirstCategory = new ArrayList<>();
        List<Translation> translationsForSecondCategory = new ArrayList<>();
        translationsForFirstCategory.add(Translation.builder()
                .id(1)
                .lang("en")
                .name("category1")
                .build());
        translationsForFirstCategory.add(Translation.builder()
                .id(2)
                .lang("ua")
                .name("категорія1")
                .build());
        translationsForSecondCategory.add(Translation.builder()
                .id(3)
                .lang("en")
                .name("category2")
                .build());
        translationsForSecondCategory.add(Translation.builder()
                .id(4)
                .lang("ua")
                .name("категорія2")
                .build());
        categories.add(Category.builder()
                .id(1)
                .status(CategoryStatus.OPENED)
                .translations(translationsForFirstCategory)
                .build());
        categories.add(Category.builder()
                .id(2)
                .status(CategoryStatus.OPENED)
                .translations(translationsForSecondCategory)
                .build());
        return categories;
    }

    public static Category createCategory() {
        List<Translation> translations = new ArrayList<>();
        translations.add(Translation.builder()
                .id(1)
                .lang("en")
                .name(NAME_EN)
                .build());
        translations.add(Translation.builder()
                .id(2)
                .lang("ua")
                .name(NAME_UA)
                .build());
        return Category.builder()
                .id(ID)
                .status(CategoryStatus.OPENED)
                .translations(translations)
                .build();
    }

    public static CategoryDto createCategoryDto() {
        CategoryDto categoryDto = new CategoryDto();
        List<TranslationDto> translations = new ArrayList<>();
        TranslationDto firstTranslationDto = new TranslationDto();
        firstTranslationDto.setLang("en");
        firstTranslationDto.setName(NAME_EN);
        TranslationDto secondTranslationDto = new TranslationDto();
        secondTranslationDto.setLang("ua");
        secondTranslationDto.setName(NAME_UA);
        translations.add(firstTranslationDto);
        translations.add(secondTranslationDto);
        categoryDto.setId(ID);
        categoryDto.setStatus(CategoryStatus.OPENED.name());
        categoryDto.setTranslations(translations);
        return categoryDto;
    }

}
