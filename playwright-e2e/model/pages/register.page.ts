import { Page } from '@playwright/test';

export class RegisterPage {
    private page: Page;

    constructor(page: Page) {
        this.page = page;
    }

    async navigateToPage(pageName: string){
        await this.page.locator('a.link').filter({hasText: pageName}).click();
    }

    async register(name: string, email: string, password: string){
        await this.page.locator('#regNameInput').fill(name);
        await this.page.locator('#regEmailInput').fill(email);
        await this.page.locator('#regPassword1').fill(password);
        await this.page.locator('#regPassword2').fill(password);

        const response = this.page.waitForResponse((res) =>
            res.url().includes('/api/auth/register'));

        await this.page.locator('.rec_button').filter({hasText: 'Регистрација'}).click();
        await response;
    }

    async registerLink(){
        await this.page.locator('a.register-button').first().click();
    }
}