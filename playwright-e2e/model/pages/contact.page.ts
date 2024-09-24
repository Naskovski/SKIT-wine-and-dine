import {expect, Page} from '@playwright/test';

export class ContactPage {
    private page: Page;

    constructor(page: Page) {
        this.page = page;
    }

    async navigateToPage(pageName: string){
        await this.page.locator('a.link').filter({hasText: pageName}).click();
    }

    async fillContactForm(subject: string, body: string) {
        await this.page.locator('#subject').fill(subject);
        await this.page.locator('#body').fill(body);

        await this.page.route('mailto:*', route => {
            const mailtoUrl = route.request().url();

            expect(mailtoUrl).toContain('winendine@gmail.com');
            expect(mailtoUrl).toContain(subject);
            expect(mailtoUrl).toContain(body);

            route.abort();
        });

        await this.page.locator('input[type="submit"]').click();
    }
}