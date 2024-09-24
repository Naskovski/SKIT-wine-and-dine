import { Page } from '@playwright/test';

export class LoginPage {
    private page: Page;

    constructor(page: Page) {
        this.page = page;
    }

    async navigateToPage(pageName: string){
        await this.page.locator('a.link').filter({hasText: pageName}).click();
    }

    async login(email: string, password: string){
        await this.page.locator('[type="email"]').fill(email);
        await this.page.locator('[type="password"]').fill(password);

        const response = this.page.waitForResponse((res) =>
            res.url().includes('/api/auth/login'));

        await this.page.locator('.rec_button').filter({hasText: 'Најава'}).click();
        await response;
    }

    async registerLink(){
        await this.page.locator('a.register-button').first().click();
    }
}