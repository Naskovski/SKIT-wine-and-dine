import {expect, Page} from '@playwright/test';

export class HomePage {
    private page: Page;

    constructor(page: Page) {
        this.page = page;
    }

    async navigateToPage(pageName: string){
        await this.page.locator('a.link').filter({hasText: pageName}).click();
    }

    async selectWineryInResultList(number: number) {
        await this.page.locator('#resultsContainer div').nth(number).click()
    }

    async selectWineryByName(name: string) {
        await this.page.locator('#resultsContainer div').filter({hasText: name}).first().click()
    }
    async checkIfActionsAreEnabled(actions: string[]) {
        const buttons = this.page.locator('.buttons button');
        for (const action of actions) {
            await expect(buttons.filter({hasText: action})).toBeEnabled();
        }
    }

    async checkIfActionsAreDisabled(actions: string[]) {
        const buttons = this.page.locator('.buttons button');
        for (const action of actions) {
            await expect(buttons.filter({hasText: action})).toBeDisabled();
        }
    }

    async clickAction(action: string) {
        await this.page.locator('.buttons button').filter({hasText: action}).click();
    }
    async getRouteAction() {
        const response = this.page.waitForResponse((res) =>
            res.url().includes('graphhopper.com'));

        await this.page.locator('.buttons button').filter({hasText: 'Добиј Рута'}).click();
        await response;
    }

    async readReviewsAction() {
        const response = this.page.waitForResponse((res) =>
            res.url().includes('/api/review/all/'));

        await this.page.locator('.buttons button').filter({hasText: 'Читај Мислење'}).click();
        await response;
    }

    async validateRouteIsDisplayed() {
        const routingLocator = this.page.locator('div.leaflet-routing-container');
        await expect(routingLocator).toBeVisible();
    }

    async validateReviewsAreDisplayed() {
        await expect(this.page.locator('.review-section')).toBeVisible()

        await expect(this.page.locator('.review-section h3')).toContainText('Просечна оценка: ');
        await expect(this.page.locator('#Review').first()).toBeVisible()
    }

    async searchWinery(query: string) {
        await this.page.locator('[name="winery-search"]').fill(query);

        const response = this.page.waitForResponse((res) =>
            res.url().includes('/api/search'));

        await this.page.locator('.search-button').click();
        await response;
    }

    async addReview(grade: number, text: string) {
        await this.clickAction('Додади Мислење');
        await this.page.waitForSelector('.review-section');
        await this.page.locator('.review-section').locator('[type="number"]').fill(String(grade));
        await this.page.locator('.review-section').locator('textarea').fill(text);

        const response = this.page.waitForResponse((res) =>
            res.url().includes('/api/review/add'));

        await this.page.locator('button').filter({hasText: 'Внеси'}).click();
        await response;
    }
}