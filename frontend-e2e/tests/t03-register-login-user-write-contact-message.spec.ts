import { test } from '@playwright/test';
import { URL } from '../model/config'
import { HomePage } from "../model/pages/home.page";
import {LoginPage} from "../model/pages/login.page";
import {RegisterPage} from "../model/pages/register.page";
import {ContactPage} from "../model/pages/contact.page";


test('Register, login user, Write contact message', async ({ page }) => {
    let homepage = new HomePage(page);
    let loginPage = new LoginPage(page);
    let registerPage = new RegisterPage(page);
    let contactPage = new ContactPage(page);

    await page.goto(URL.home);

    await homepage.navigateToPage('Профил');
    await loginPage.registerLink();

    const userRandomNumber = Math.ceil(Math.random()*10000);
    await registerPage.register('User'+userRandomNumber, userRandomNumber+'@mail.com', 'verySecurePassword');
    await registerPage.navigateToPage('Профил');
    await loginPage.login(userRandomNumber+'@mail.com', 'verySecurePassword');

    await homepage.navigateToPage('Контакт');
    await contactPage.fillContactForm("example subject", "placeholder message");
});
