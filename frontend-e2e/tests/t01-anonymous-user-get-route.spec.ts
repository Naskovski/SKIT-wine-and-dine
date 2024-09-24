import { test } from '@playwright/test';
import { URL } from '../model/config'
import { HomePage } from "../model/pages/home.page";


test('Anonymous User, get route', async ({ page }) => {
    let homepage = new HomePage(page);

    await page.goto(URL.home);

    await homepage.selectWineryByName('Winery „Popov“');

    await homepage.checkIfActionsAreEnabled(['Добиј Рута', 'Читај Мислење']);
    await homepage.checkIfActionsAreDisabled(['Додади Мислење']);

    await homepage.getRouteAction();
    await homepage.validateRouteIsDisplayed();
});
