import { Page } from '@playwright/test';

export class ProfilePage {
    private page: Page;

    constructor(page: Page) {
        this.page = page;
    }

    async navigateToPage(pageName: string){
        await this.page.locator('a.link').filter({hasText: pageName}).click();
    }

    async logout(){
        await this.page.locator('button').filter({hasText: 'Одјава'}).click();
    }
}